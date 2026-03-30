package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.dto.EnrollmentDTO;
import vn.edu.ute.productmgmt.model.entity.Enrollment;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

public class EnrollmentDao extends BaseDao<Enrollment, Long> {
    public EnrollmentDao() { super(Enrollment.class); }

    public boolean existsByStudentAndClassSection(Long studentId, Long classSectionId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(e) FROM Enrollment e "
                                    + "WHERE e.student.id = :sid AND e.classSection.id = :cid",
                            Long.class)
                    .setParameter("sid", studentId)
                    .setParameter("cid", classSectionId)
                    .getSingleResult();
            return count != null && count > 0;
        } finally {
            em.close();
        }
    }

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
}
