package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.EnrollmentDao;
import vn.edu.ute.productmgmt.model.dto.EnrollmentDTO;
import vn.edu.ute.productmgmt.model.entity.Enrollment;
import vn.edu.ute.productmgmt.model.enums.EnrollmentStatus;
import java.util.List;

public class TranscriptService {
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();

    public List<EnrollmentDTO> getStudentTranscript(Long studentId) {
        return enrollmentDao.findTranscriptByStudent(studentId);
    }

    public List<EnrollmentDTO> getEnrollmentsByClass(Long classSectionId) {
        return enrollmentDao.findByClassSection(classSectionId);
    }

    public Double calculateSemesterGPA(List<EnrollmentDTO> transcript) {
        double totalPoints = 0;
        int totalCredits = 0;
        for (EnrollmentDTO dto : transcript) {
            if (dto.getScore() != null) {
                double scale4 = convertToScale4(dto.getScore());
                totalPoints += scale4 * dto.getCredits();
                totalCredits += dto.getCredits();
            }
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    private double convertToScale4(Double score) {
        if (score >= 8.5) return 4.0;    // A
        if (score >= 8.0) return 3.5;    // B+
        if (score >= 7.0) return 3.0;    // B
        if (score >= 6.5) return 2.5;    // C+
        if (score >= 5.5) return 2.0;    // C
        if (score >= 5.0) return 1.5;    // D+
        if (score >= 4.0) return 1.0;    // D
        return 0.0;                      // F
    }

    public String updateEnrollmentScore(Long enrollmentId, Double score) {
        if (score < 0 || score > 10) {
            return "Điểm phải nằm trong khoảng từ 0 đến 10!";
        }
        try {
            Enrollment enrollment = enrollmentDao.findById(enrollmentId);
            if (enrollment == null) return "Không tìm thấy nội dung đăng ký này!";
            
            enrollment.setScore(score);
            if (score >= 5.0) {
                enrollment.setStatus(EnrollmentStatus.PASSED);
            } else {
                enrollment.setStatus(EnrollmentStatus.FAILED);
            }
            enrollmentDao.save(enrollment);
            return "SUCCESS";
        } catch (Exception e) {
            return "Lỗi cập nhật điểm: " + e.getMessage();
        }
    }
}