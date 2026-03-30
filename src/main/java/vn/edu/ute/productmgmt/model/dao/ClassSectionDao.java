package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;
import vn.edu.ute.productmgmt.model.entity.ClassSection;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

public class ClassSectionDao extends BaseDao<ClassSection, Long> {
    public ClassSectionDao() { super(ClassSection.class); }

    private static final String DTO_SELECT = "SELECT new vn.edu.ute.productmgmt.model.dto.ClassSectionDTO(" +
            "cs.id, cs.classCode, c.title, c.credits, l.fullName, l.lecturerCode, sem.semesterName, " +
            "cs.dayOfWeek, cs.startPeriod, cs.endPeriod, cs.room, " +
            "(SELECT COUNT(e) FROM Enrollment e WHERE e.classSection.id = cs.id), " +
            "cs.maxCapacity) " +
            "FROM ClassSection cs " +
            "JOIN cs.course c " +
            "JOIN cs.lecturer l " +
            "JOIN cs.semester sem ";

    public List<ClassSectionDTO> findAllDTO() {
        EntityManager em = JpaUtil.getEntityManager();
        try { return em.createQuery(DTO_SELECT, ClassSectionDTO.class).getResultList(); }
        finally { em.close(); }
    }

    public List<ClassSectionDTO> findDTOBySemester(Long semesterId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(DTO_SELECT + " WHERE sem.id = :semId", ClassSectionDTO.class)
                    .setParameter("semId", semesterId)
                    .getResultList();
        } finally { em.close(); }
    }

    public List<ClassSectionDTO> findByLecturerId(Long lecturerId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(DTO_SELECT + " WHERE l.id = :lid", ClassSectionDTO.class)
                    .setParameter("lid", lecturerId)
                    .getResultList();
        } finally { em.close(); }
    }

    /**
     * Hàm quan trọng để Service kiểm tra sĩ số lớp
     */
    public long countEnrollmentByClassId(Long classSectionId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(e) FROM Enrollment e WHERE e.classSection.id = :cid", Long.class)
                    .setParameter("cid", classSectionId)
                    .getSingleResult();
        } finally { em.close(); }
    }

    public long countTotalClasses() {
        EntityManager em = JpaUtil.getEntityManager();
        try { return em.createQuery("SELECT COUNT(cs) FROM ClassSection cs", Long.class).getSingleResult(); }
        finally { em.close(); }
    }
}