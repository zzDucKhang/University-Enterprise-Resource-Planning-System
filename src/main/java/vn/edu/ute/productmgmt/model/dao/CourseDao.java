package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.entity.Course;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

public class CourseDao extends BaseDao<Course, Long> {
    public CourseDao() { super(Course.class); }

    // Tìm môn học theo Mã môn (CourseCode)
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