package vn.edu.ute.productmgmt.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import vn.edu.ute.productmgmt.model.dao.ClassSectionDao;
import vn.edu.ute.productmgmt.model.dao.EnrollmentDao;
import vn.edu.ute.productmgmt.model.dto.EnrollmentDTO;
import vn.edu.ute.productmgmt.model.entity.ClassSection;
import vn.edu.ute.productmgmt.model.entity.Course;
import vn.edu.ute.productmgmt.model.entity.Enrollment;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.enums.EnrollmentStatus;
import vn.edu.ute.productmgmt.model.util.JpaUtil;

import java.time.LocalDate;
import java.util.List;

public class RegistrationService {
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final ClassSectionDao classSectionDao = new ClassSectionDao();

    // Đăng ký học phần cho student
    public String registerCourse(Student student, Long classSectionId) {

        // --- PHẦN 1: KIỂM TRA ĐIỀU KIỆN (READ-ONLY) ---
        ClassSection section = classSectionDao.findById(classSectionId);
        if (section == null) return "Không tìm thấy lớp học phần này!";

        // 1. Kiểm tra sĩ số nhanh
        int currentCount = (section.getCurrentEnrollment() != null) ? section.getCurrentEnrollment() : 0;
        if (currentCount >= section.getMaxCapacity()) {
            return "Lớp học phần này đã đầy sĩ số!";
        }

        // 2. Kiểm tra thời gian
        if (section.getSemester().getRegistrationStartDate() != null && section.getSemester().getRegistrationEndDate() != null) {
            LocalDate today = LocalDate.now();
            if (today.isBefore(section.getSemester().getRegistrationStartDate()) || today.isAfter(section.getSemester().getRegistrationEndDate())) {
                return "Hiện không phải là thời gian đăng ký tín chỉ cho học kỳ này!";
            }
        }

        List<Enrollment> currentSemesterRegistrations = enrollmentDao.findAll().stream()
                .filter(e -> e.getStudent().getId().equals(student.getId())
                        && e.getClassSection().getSemester().getId().equals(section.getSemester().getId()))
                .toList();

        List<EnrollmentDTO> studentTranscript = enrollmentDao.findTranscriptByStudent(student.getId());

        // 3. Trùng môn
        boolean duplicateCourse = currentSemesterRegistrations.stream()
                .anyMatch(e -> e.getClassSection().getCourse().getId().equals(section.getCourse().getId()));
        if (duplicateCourse) return "Bạn đã đăng ký một lớp khác của môn học này trong học kỳ hiện tại!";

        // 4. Đã qua môn
        boolean alreadyPassed = studentTranscript.stream()
                .anyMatch(e -> e.getCourseCode().equals(section.getCourse().getCourseCode())
                        && e.getStatus() == EnrollmentStatus.PASSED);
        if (alreadyPassed) return "Bạn đã hoàn thành qua môn học này, không được đăng ký học lại!";

        // 5. Môn tiên quyết
        for (Course prerequisite : section.getCourse().getPrerequisites()) {
            boolean passed = studentTranscript.stream()
                    .anyMatch(e -> e.getCourseCode().equals(prerequisite.getCourseCode())
                            && e.getStatus() == EnrollmentStatus.PASSED);
            if (!passed) return "Bạn chưa hoàn thành môn tiên quyết: " + prerequisite.getTitle();
        }

        // 6. Giới hạn tín chỉ
        int currentCredits = currentSemesterRegistrations.stream()
                .mapToInt(e -> e.getClassSection().getCourse().getCredits()).sum();
        if (currentCredits + section.getCourse().getCredits() > 24) {
            return "Vượt quá giới hạn tín chỉ đăng ký cho phép của học kỳ (Tối đa 24 tín chỉ)!";
        }

        // 7. Trùng lịch
        for (Enrollment reg : currentSemesterRegistrations) {
            ClassSection s = reg.getClassSection();
            if (s.getDayOfWeek() == section.getDayOfWeek()) {
                if (Math.max(s.getStartPeriod(), section.getStartPeriod()) <= Math.min(s.getEndPeriod(), section.getEndPeriod())) {
                    return "Bị trùng lịch học với môn: " + s.getCourse().getTitle();
                }
            }
        }

        // --- PHẦN 2: LƯU DỮ LIỆU ĐỒNG BỘ BẰNG TRANSACTION TỰ QUẢN ---
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Lấy lại ClassSection VÀO TRONG kết nối hiện tại để tránh lỗi Lazy
            ClassSection managedSection = em.find(ClassSection.class, classSectionId);

            // Double-check sĩ số phòng khi có người vừa giành mất chỗ 1 giây trước
            int latestCount = (managedSection.getCurrentEnrollment() != null) ? managedSection.getCurrentEnrollment() : 0;
            if (latestCount >= managedSection.getMaxCapacity()) {
                em.getTransaction().rollback();
                return "Lớp học phần này đã đầy sĩ số!";
            }

            // A. Lưu bảng Đăng ký (Enrollment) trực tiếp bằng EntityManager
            Enrollment newEnrollment = new Enrollment();
            newEnrollment.setStudent(student);
            newEnrollment.setClassSection(managedSection);
            newEnrollment.setStatus(EnrollmentStatus.REGISTERED);
            em.persist(newEnrollment);

            // B. Cập nhật sĩ số cho lớp (Kích hoạt khóa @Version)
            managedSection.setCurrentEnrollment(latestCount + 1);
            em.merge(managedSection);

            // C. Xác nhận lưu cả 2 xuống Database cùng lúc
            em.getTransaction().commit();
            return "SUCCESS";

        } catch (OptimisticLockException ole) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "Lớp học vừa có người đăng ký hoặc thông tin đã thay đổi. Vui lòng làm mới lại trang!";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return "Lỗi đăng ký hệ thống: " + e.getMessage();
        } finally {
            em.close();
        }
    }
}