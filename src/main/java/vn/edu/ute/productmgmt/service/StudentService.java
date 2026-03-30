package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.StudentDao;
import vn.edu.ute.productmgmt.model.dto.StudentDTO;
import vn.edu.ute.productmgmt.model.entity.Account;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.enums.UserRole;
import vn.edu.ute.productmgmt.model.util.PasswordUtil;
import vn.edu.ute.productmgmt.model.util.ValidationUtil;
import java.util.List;

public class StudentService {
    private final StudentDao studentDao = new StudentDao();
    // Khai báo AccountService làm field để tái sử dụng
    private final AccountService accountService = new AccountService();

    public List<StudentDTO> getAllStudentDTOs() {
        return studentDao.findAllDTO();
    }

    public Student findByCode(String code) {
        return studentDao.findByStudentCode(code);
    }

    public List<StudentDTO> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudentDTOs();
        }
        return studentDao.searchByNameOrCode(keyword);
    }

    public long countTotal() {
        return studentDao.countAllStudents();
    }

    public List<StudentDTO> getTop10StudentsByGPA() {
        return getAllStudentDTOs().stream()
                .filter(s -> s.getGpa() != null && s.getGpa() > 0)
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .limit(10)
                .toList();
    }

    // Hàm tạo student mới
    public String saveStudent(Student student) {
        // 1. Validation logic
        if (!ValidationUtil.isValidStudentCode(student.getStudentCode())) {
            return "Mã sinh viên phải đúng 8 chữ số!";
        }
        if (!ValidationUtil.isValidEmail(student.getEmail())) {
            return "Email không đúng định dạng nhà trường!";
        }

        boolean isNewStudent = (student.getId() == null);

        if (isNewStudent) {
            if (studentDao.findByStudentCode(student.getStudentCode()) != null) {
                return "Mã sinh viên này đã tồn tại trong hệ thống!";
            }
        }

        // Tạo account cho student với tài khoản là mã sinh viên và mật khẩu mặt định 123
        try {
            if (isNewStudent) {
                Account newAccount = new Account();
                newAccount.setUsername(student.getStudentCode());
                //BĂM MẬT KHẨU
                newAccount.setPassword(PasswordUtil.hashPassword("123"));
                newAccount.setRole(UserRole.STUDENT);

                // Gắn Account vào Student
                student.setAccount(newAccount);
            }
            studentDao.save(student);

            return "SUCCESS";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi hệ thống khi lưu sinh viên: " + e.getMessage();
        }
    }

    public void deleteStudent(Long id) {
        studentDao.deleteById(id);
    }
}