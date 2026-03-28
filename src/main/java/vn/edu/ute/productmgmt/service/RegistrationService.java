package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.ClassSectionDao;
import vn.edu.ute.productmgmt.model.dao.EnrollmentDao;
import vn.edu.ute.productmgmt.model.entity.ClassSection;
import vn.edu.ute.productmgmt.model.entity.Course;
import vn.edu.ute.productmgmt.model.entity.Enrollment;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.enums.EnrollmentStatus;
import java.util.List;

public class RegistrationService {
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final ClassSectionDao classSectionDao = new ClassSectionDao();

    public String registerCourse(Student student, Long classSectionId) {
        ClassSection section = classSectionDao.findById(classSectionId);

        // 1. Kiểm tra sĩ số lớp
        long currentEnrollment = classSectionDao.countEnrollmentByClassId(classSectionId);

        if (currentEnrollment >= section.getMaxCapacity()) {
            return "Lớp học phần này đã đầy sĩ số!";
        }

        // 2. Kiểm tra môn tiên quyết
        for (Course prerequisite : section.getCourse().getPrerequisites()) {
            boolean passed = enrollmentDao.findTranscriptByStudent(student.getId()).stream()
                    .anyMatch(e -> e.getCourseCode().equals(prerequisite.getCourseCode())
                            && e.getStatus() == EnrollmentStatus.PASSED);
            if (!passed) {
                return "Bạn chưa hoàn thành môn tiên quyết: " + prerequisite.getTitle();
            }
        }

        // 3. Kiểm tra trùng lịch học
        List<Enrollment> registrations = enrollmentDao.findAll().stream()
                .filter(e -> e.getStudent().getId().equals(student.getId())
                        && e.getClassSection().getSemester().getId().equals(section.getSemester().getId()))
                .toList();

        for (Enrollment reg : registrations) {
            ClassSection s = reg.getClassSection();
            if (s.getDayOfWeek() == section.getDayOfWeek()) {
                // Công thức kiểm tra giao thoa khoảng tiết học
                if (Math.max(s.getStartPeriod(), section.getStartPeriod()) <= Math.min(s.getEndPeriod(), section.getEndPeriod())) {
                    return "Bị trùng lịch với môn: " + s.getCourse().getTitle();
                }
            }
        }

        // 4. Nếu vượt qua tất cả, tiến hành lưu
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