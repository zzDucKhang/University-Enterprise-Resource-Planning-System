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

        var transcript = enrollmentDao.findTranscriptByStudent(student.getId());
        for (Course prerequisite : section.getCourse().getPrerequisites()) {
            boolean passed = transcript.stream()
                    .anyMatch(e -> e.getCourseCode().equals(prerequisite.getCourseCode())
                            && e.getStatus() == EnrollmentStatus.PASSED);
            if (!passed) {
                return "Bạn chưa hoàn thành môn tiên quyết: " + prerequisite.getTitle();
            }
        }

        List<Enrollment> registrations = enrollmentDao.findAll().stream()
                .filter(e -> e.getStudent().getId().equals(student.getId())
                        && e.getClassSection().getSemester().getId().equals(section.getSemester().getId()))
                .toList();

        for (Enrollment reg : registrations) {
            ClassSection s = reg.getClassSection();
            if (s.getDayOfWeek() == section.getDayOfWeek()) {
                Integer sp = s.getStartPeriod();
                Integer ep = s.getEndPeriod();
                Integer a = section.getStartPeriod();
                Integer b = section.getEndPeriod();
                if (sp != null && ep != null && a != null && b != null
                        && Math.max(sp, a) <= Math.min(ep, b)) {
                    return "Bị trùng lịch với môn: " + s.getCourse().getTitle();
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
