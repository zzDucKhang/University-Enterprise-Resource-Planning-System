package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.entity.Course;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

// Học phần
public class CourseDao extends BaseDao<Course, Long> {
    public CourseDao() { super(Course.class); }

    // Lấy toàn bộ danh sách các môn học, và "gom" luôn cả danh sách các môn tiên quyết (Prerequisites) của từng môn đó
    public List<Course> findAllWithPrerequisites() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.prerequisites", Course.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm kiếm môn học bằng mã môn
    public Course findByCourseCode(String code) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Course c WHERE c.courseCode = :code", Course.class)
                    .setParameter("code", code)
                    .getSingleResult();
        } catch (Exception e) { return null; }
        finally { em.close(); }
    }
}