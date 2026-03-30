package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.dto.EnrollmentDTO;
import vn.edu.ute.productmgmt.model.entity.Enrollment;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

// Điểm
public class EnrollmentDao extends BaseDao<Enrollment, Long> {
    public EnrollmentDao() { super(Enrollment.class); }

    // Xuất bảng điểm của mỗi môn học cho sinh viên
    public List<EnrollmentDTO> findTranscriptByStudent(Long studentId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new vn.edu.ute.productmgmt.model.dto.EnrollmentDTO(" +
                    "c.courseCode, c.title, c.credits, e.score, e.status) " +
                    "FROM Enrollment e JOIN e.classSection cs JOIN cs.course c " +
                    "WHERE e.student.id = :sid ORDER BY cs.semester.id DESC";
            return em.createQuery(jpql, EnrollmentDTO.class)
                    .setParameter("sid", studentId)
                    .getResultList();
        } finally { em.close(); }
    }

    // Xuất "Danh sách sinh viên" (Bảng chấm điểm) cho Giảng viên.
    public List<EnrollmentDTO> findByClassSection(Long classSectionId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new vn.edu.ute.productmgmt.model.dto.EnrollmentDTO(" +
                    "e.id, s.studentCode, s.fullName, e.score, e.status) " +
                    "FROM Enrollment e JOIN e.student s " +
                    "WHERE e.classSection.id = :cId ORDER BY s.studentCode ASC";
            return em.createQuery(jpql, EnrollmentDTO.class)
                    .setParameter("cId", classSectionId)
                    .getResultList();
        } finally { em.close(); }
    }
}