package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;
import vn.edu.ute.productmgmt.model.entity.ClassSection;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

public class ClassSectionDao extends BaseDao<ClassSection, Long> {
    public ClassSectionDao() { super(ClassSection.class); }

    // HÀM CÒN THIẾU ĐỂ FIX LỖI TRONG REGISTRATION SERVICE
    public List<ClassSectionDTO> findAllDTO() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new vn.edu.ute.productmgmt.model.dto.ClassSectionDTO(" +
                    "cs.id, cs.classCode, c.title, c.credits, l.fullName, sem.semesterName, " +
                    "cs.dayOfWeek, cs.startPeriod, cs.endPeriod, cs.room, " +
                    "(SELECT COUNT(e) FROM Enrollment e WHERE e.classSection.id = cs.id), " +
                    "cs.maxCapacity) " +
                    "FROM ClassSection cs " +
                    "JOIN cs.course c " +
                    "JOIN cs.lecturer l " +
                    "JOIN cs.semester sem";
            return em.createQuery(jpql, ClassSectionDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<ClassSectionDTO> findDTOBySemester(Long semesterId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new vn.edu.ute.productmgmt.model.dto.ClassSectionDTO(" +
                    "cs.id, cs.classCode, c.title, c.credits, l.fullName, sem.semesterName, " +
                    "cs.dayOfWeek, cs.startPeriod, cs.endPeriod, cs.room, " +
                    "(SELECT COUNT(e) FROM Enrollment e WHERE e.classSection.id = cs.id), " +
                    "cs.maxCapacity) " +
                    "FROM ClassSection cs " +
                    "JOIN cs.course c " +
                    "JOIN cs.lecturer l " +
                    "JOIN cs.semester sem " +
                    "WHERE sem.id = :semId";
            return em.createQuery(jpql, ClassSectionDTO.class)
                    .setParameter("semId", semesterId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long countEnrollmentByClassId(Long classId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // Câu query này chỉ trả về đúng 1 con số duy nhất
            String jpql = "SELECT COUNT(e) FROM Enrollment e WHERE e.classSection.id = :cid";
            return em.createQuery(jpql, Long.class)
                    .setParameter("cid", classId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}