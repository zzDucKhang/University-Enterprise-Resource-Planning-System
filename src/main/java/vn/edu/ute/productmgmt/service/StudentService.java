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

    public String saveStudent(Student student) {
        // Validation logic
        if (!ValidationUtil.isValidStudentCode(student.getStudentCode())) {
            return "Mã sinh viên phải đúng 8 chữ số!";
        }
        if (!ValidationUtil.isValidEmail(student.getEmail())) {
            return "Email không đúng định dạng nhà trường!";
        }

        // Kiểm tra trùng mã sinh viên khi thêm mới
        if (student.getId() == null) {
            if (studentDao.findByStudentCode(student.getStudentCode()) != null) {
                return "Mã sinh viên này đã tồn tại!";
            }
        }

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