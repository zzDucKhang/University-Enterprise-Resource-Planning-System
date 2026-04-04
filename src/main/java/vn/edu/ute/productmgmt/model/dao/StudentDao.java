package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.dto.StudentDTO;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.enums.EnrollmentStatus;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

public class StudentDao extends BaseDao<Student, Long> {
    public StudentDao() { super(Student.class); }

    // Tìm theo MSSV
    public Student findByStudentCode(String code) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Student s WHERE s.studentCode = :code", Student.class)
                    .setParameter("code", code)
                    .getSingleResult();
        } catch (Exception e) { return null; }
        finally { em.close(); }
    }

    // Lấy danh sách DTO kèm tính GPA (Chỉ tính môn đã Đạt)
    // Lấy danh sách DTO kèm tính GPA có trọng số (Tính cả môn Đạt và Rớt)
    public List<StudentDTO> findAllDTO() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new vn.edu.ute.productmgmt.model.dto.StudentDTO(" +
                    "s.id, s.studentCode, s.fullName, s.dob, s.email, s.gender, " +
                    "m.name, f.name, " +
                    "(SUM(en.score * c.credits) / SUM(c.credits)), SUM(c.credits)) " +
                    "FROM Student s " +
                    "JOIN s.major m " +
                    "JOIN m.faculty f " +
                    "LEFT JOIN s.enrollments en ON en.status IN (:passed, :failed) " + // Lấy cả môn Đạt và Rớt
                    "LEFT JOIN en.classSection cs " +
                    "LEFT JOIN cs.course c " +
                    "GROUP BY s.id, s.studentCode, s.fullName, s.dob, s.email, s.gender, m.name, f.name";
            return em.createQuery(jpql, StudentDTO.class)
                    .setParameter("passed", EnrollmentStatus.PASSED)
                    .setParameter("failed", EnrollmentStatus.FAILED)
                    .setHint("jakarta.persistence.cache.retrieveMode", "BYPASS") // Ép JPA lấy dữ liệu mới nhất, bỏ qua Cache
                    .getResultList();
        } finally { em.close(); }
    }

    // Tìm kiếm sinh viên bằng name hoặc mã sinh viên
    public List<StudentDTO> searchByNameOrCode(String keyword) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new vn.edu.ute.productmgmt.model.dto.StudentDTO(" +
                    "s.id, s.studentCode, s.fullName, s.dob, s.email, s.gender, " +
                    "m.name, f.name, " +
                    "(SUM(en.score * c.credits) / SUM(c.credits)), SUM(c.credits)) " +
                    "FROM Student s " +
                    "JOIN s.major m " +
                    "JOIN m.faculty f " +
                    "LEFT JOIN s.enrollments en ON en.status IN (:passed, :failed) " +
                    "LEFT JOIN en.classSection cs " +
                    "LEFT JOIN cs.course c " +
                    "WHERE s.fullName LIKE :key OR s.studentCode LIKE :key " +
                    "GROUP BY s.id, s.studentCode, s.fullName, s.dob, s.email, s.gender, m.name, f.name";
            return em.createQuery(jpql, StudentDTO.class)
                    .setParameter("passed", EnrollmentStatus.PASSED)
                    .setParameter("failed", EnrollmentStatus.FAILED)
                    .setParameter("key", "%" + keyword + "%")
                    .setHint("jakarta.persistence.cache.retrieveMode", "BYPASS")
                    .getResultList();
        } finally { em.close(); }
    }

    // đếm tổng số sinh viên
    public long countAllStudents() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(s) FROM Student s", Long.class).getSingleResult();
        } finally { em.close(); }
    }
}