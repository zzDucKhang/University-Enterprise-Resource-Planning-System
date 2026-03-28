package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.EnrollmentDao;
import vn.edu.ute.productmgmt.model.dto.EnrollmentDTO;
import java.util.List;

public class TranscriptService {
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();

    public List<EnrollmentDTO> getStudentTranscript(Long studentId) {
        return enrollmentDao.findTranscriptByStudent(studentId);
    }

    public Double calculateSemesterGPA(List<EnrollmentDTO> transcript) {
        double totalPoints = 0;
        int totalCredits = 0;
        for (EnrollmentDTO dto : transcript) {
            if (dto.getScore() != null) {
                totalPoints += dto.getScore() * dto.getCredits();
                totalCredits += dto.getCredits();
            }
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}