package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.entity.Course;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

public class CourseDao extends BaseDao<Course, Long> {
    public CourseDao() { super(Course.class); }

    // HÀM CÒN THIẾU ĐỂ FIX LỖI TRONG COURSE SERVICE
    public List<Course> findAllWithPrerequisites() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // Sử dụng FETCH JOIN để lấy luôn danh sách prerequisites trong 1 câu query
            return em.createQuery("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.prerequisites", Course.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

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