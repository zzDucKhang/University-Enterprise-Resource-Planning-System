package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.StudentDao;
import vn.edu.ute.productmgmt.model.dto.StudentDTO;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.util.ValidationUtil;
import java.util.List;

public class StudentService {
    private final StudentDao studentDao = new StudentDao();

    public List<StudentDTO> getAllStudentDTOs() {
        return studentDao.findAllDTO();
    }

    /**
     * Tìm sinh viên theo mã (MSSV)
     * Dùng để hỗ trợ hàm Update và Delete trong Controller
     */
    public Student findByCode(String code) {
        return studentDao.findByStudentCode(code);
    }

    /**
     * Tìm kiếm sinh viên theo tên hoặc mã (Dùng JPQL LIKE)
     */
    public List<StudentDTO> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudentDTOs();
        }
        return studentDao.searchByNameOrCode(keyword);
    }

    /**
     * Đếm tổng số sinh viên (Dùng cho StatisticPanel)
     */
    public long countTotal() {
        // Gọi hàm đếm trực tiếp từ DB để tối ưu hiệu năng
        return studentDao.countAllStudents();
    }

    public String saveStudent(Student student) {
        // 1. Validation logic
        if (!ValidationUtil.isValidStudentCode(student.getStudentCode())) {
            return "Mã sinh viên phải đúng 8 chữ số!";
        }
        if (!ValidationUtil.isValidEmail(student.getEmail())) {
            return "Email không đúng định dạng nhà trường!";
        }

        // 2. Kiểm tra trùng mã khi THÊM MỚI (id == null)
        if (student.getId() == null) {
            if (studentDao.findByStudentCode(student.getStudentCode()) != null) {
                return "Mã sinh viên này đã tồn tại!";
            }
        }
        // 3. Nếu là CẬP NHẬT (id != null), bỏ qua bước check trùng mã chính nó

        try {
            studentDao.save(student);
            return "SUCCESS";
        } catch (Exception e) {
            return "Lỗi hệ thống: " + e.getMessage();
        }
    }

    public void deleteStudent(Long id) {
        studentDao.deleteById(id);
    }
}