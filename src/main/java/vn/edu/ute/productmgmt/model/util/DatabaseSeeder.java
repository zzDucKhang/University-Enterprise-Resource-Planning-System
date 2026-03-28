package vn.edu.ute.productmgmt.model.util;

import vn.edu.ute.productmgmt.model.dao.*;
import vn.edu.ute.productmgmt.model.entity.*;
import vn.edu.ute.productmgmt.model.enums.Gender;
import vn.edu.ute.productmgmt.model.enums.UserRole;

public class DatabaseSeeder {

    public static void seed() {
        FacultyDao facultyDao = new FacultyDao();
        AccountDao accountDao = new AccountDao();
        MajorDao majorDao = new MajorDao();
        StudentDao studentDao = new StudentDao();

        // 1. Seed Khoa (Nếu chưa có khoa nào)


            // 3. Seed Tài khoản Admin (Mật khẩu: 123)
        if (accountDao.findByUsername("admin") == null) {
                Account admin = new Account();
                admin.setUsername("admin");
                // Java tự hash ở đây, nên chắc chắn khớp!
                admin.setPassword(PasswordUtil.hashPassword("123"));
                admin.setRole(UserRole.ADMIN);
                accountDao.save(admin);
            }

            // 4. Seed Sinh viên mẫu & Tài khoản SV (Mật khẩu: 123)
            String studentCode = "24110239";

    }
}