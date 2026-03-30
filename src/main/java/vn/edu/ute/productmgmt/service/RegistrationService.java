package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.ClassSectionDao;
import vn.edu.ute.productmgmt.model.dao.EnrollmentDao;
import vn.edu.ute.productmgmt.model.entity.ClassSection;
import vn.edu.ute.productmgmt.model.entity.Course;
import vn.edu.ute.productmgmt.model.entity.Enrollment;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.enums.EnrollmentStatus;
import java.time.LocalDate;
import java.util.List;
public class RegistrationService {
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final ClassSectionDao classSectionDao = new ClassSectionDao();

    public String registerCourse(Student student, Long classSectionId) {
        if (student == null || classSectionId == null) {
            return "Dữ liệu đăng ký không hợp lệ!";
        }

        ClassSection section = classSectionDao.findById(classSectionId);
        if (section == null) {
            return "Không tìm thấy lớp học phần!";
        }

        if (enrollmentDao.existsByStudentAndClassSection(student.getId(), classSectionId)) {
            return "Bạn đã đăng ký lớp học phần này rồi!";
        }

        long currentEnrollment = classSectionDao.countEnrollmentByClassId(classSectionId);
        Integer cap = section.getMaxCapacity();
        if (cap != null && currentEnrollment >= cap) {
            return "Lớp học phần này đã đầy sĩ số!";
        }

<<<<<<< HEAD
        var transcript = enrollmentDao.findTranscriptByStudent(student.getId());
=======
        // 2. Tùy chỉnh: Kiểm tra thời gian mở đăng ký tín chỉ
        if (section.getSemester().getRegistrationStartDate() != null && section.getSemester().getRegistrationEndDate() != null) {
            LocalDate today = LocalDate.now();
            if (today.isBefore(section.getSemester().getRegistrationStartDate()) || today.isAfter(section.getSemester().getRegistrationEndDate())) {
                return "Hiện không phải là thời gian đăng ký tín chỉ cho học kỳ này!";
            }
        }

        // Lấy danh sách đăng ký hiện tại của sinh viên trong HỌC KỲ NÀY
        List<Enrollment> registrations = enrollmentDao.findAll().stream()
                .filter(e -> e.getStudent().getId().equals(student.getId())
                        && e.getClassSection().getSemester().getId().equals(section.getSemester().getId()))
                .toList();

        // 3. Tùy chỉnh: Đăng ký trùng môn học (cùng môn khác lớp)
        boolean duplicateCourse = registrations.stream().anyMatch(e -> e.getClassSection().getCourse().getId().equals(section.getCourse().getId()));
        if (duplicateCourse) {
            return "Bạn đã đăng ký một lớp khác của môn học này trong học kỳ hiện tại!";
        }

        // 4. Tùy chỉnh: Môn học đã học và Passed chưa?
        boolean alreadyPassed = enrollmentDao.findTranscriptByStudent(student.getId()).stream()
                .anyMatch(e -> e.getCourseCode().equals(section.getCourse().getCourseCode())
                        && e.getStatus() == EnrollmentStatus.PASSED);
        if (alreadyPassed) {
            return "Bạn đã hoàn thành qua môn học này, không được đăng ký học lại!";
        }

        // 5. Kiểm tra môn tiên quyết
>>>>>>> e42212cd2ac4d13cca7404df0da395bf07b0f515
        for (Course prerequisite : section.getCourse().getPrerequisites()) {
            boolean passed = transcript.stream()
                    .anyMatch(e -> e.getCourseCode().equals(prerequisite.getCourseCode())
                            && e.getStatus() == EnrollmentStatus.PASSED);
            if (!passed) {
                return "Bạn chưa hoàn thành môn tiên quyết: " + prerequisite.getTitle();
            }
        }

<<<<<<< HEAD
        List<Enrollment> registrations = enrollmentDao.findAll().stream()
                .filter(e -> e.getStudent().getId().equals(student.getId())
                        && e.getClassSection().getSemester().getId().equals(section.getSemester().getId()))
                .toList();
=======
        // 6. Kiểm tra giới hạn Tín chỉ (MAX: 24)
        int currentCredits = registrations.stream().mapToInt(e -> e.getClassSection().getCourse().getCredits()).sum();
        if (currentCredits + section.getCourse().getCredits() > 24) {
            return "Vượt quá giới hạn tín chỉ đăng ký cho phép của học kỳ (Tối đa 24 tín chỉ)!";
        }
>>>>>>> e42212cd2ac4d13cca7404df0da395bf07b0f515

        // 7. Kiểm tra trùng lịch học
        for (Enrollment reg : registrations) {
            ClassSection s = reg.getClassSection();
            if (s.getDayOfWeek() == section.getDayOfWeek()) {
<<<<<<< HEAD
                Integer sp = s.getStartPeriod();
                Integer ep = s.getEndPeriod();
                Integer a = section.getStartPeriod();
                Integer b = section.getEndPeriod();
                if (sp != null && ep != null && a != null && b != null
                        && Math.max(sp, a) <= Math.min(ep, b)) {
                    return "Bị trùng lịch với môn: " + s.getCourse().getTitle();
=======
                // Công thức kiểm tra giao thoa khoảng tiết học
                if (Math.max(s.getStartPeriod(), section.getStartPeriod()) <= Math.min(s.getEndPeriod(), section.getEndPeriod())) {
                    return "Bị trùng lịch học với môn: " + s.getCourse().getTitle();
>>>>>>> e42212cd2ac4d13cca7404df0da395bf07b0f515
                }
            }
        }

        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setStudent(student);
        newEnrollment.setClassSection(section);
        newEnrollment.setStatus(EnrollmentStatus.REGISTERED);

        try {
            enrollmentDao.save(newEnrollment);
            return "SUCCESS";
        } catch (Exception e) {
            return "Lỗi đăng ký: " + e.getMessage();
        }
    }
}
