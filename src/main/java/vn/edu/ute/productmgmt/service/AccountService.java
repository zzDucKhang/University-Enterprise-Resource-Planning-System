package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.AccountDao;
import vn.edu.ute.productmgmt.model.dto.AccountDTO;
import vn.edu.ute.productmgmt.model.entity.Account;
import vn.edu.ute.productmgmt.model.util.PasswordUtil;
import vn.edu.ute.productmgmt.model.util.SessionManager;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();

    public String login(String username, String password) {
        // 1. Tìm tài khoản trong DB (Nhờ có JOIN FETCH trong DAO nên sẽ có đủ Student/Lecturer)
        Account acc = accountDao.findByUsername(username);

        if (acc == null) {
            return "Tài khoản không tồn tại!";
        }

        // 2. Kiểm tra mật khẩu (Sử dụng PasswordUtil để check hash)
        if (!PasswordUtil.checkPassword(password, acc.getPassword())) {
            return "Mật khẩu không chính xác!";
        }

        // 3. Xác định tên chủ sở hữu (Owner Name)
        String ownerName = "Admin";
        if (acc.getStudent() != null) {
            ownerName = acc.getStudent().getFullName();
        } else if (acc.getLecturer() != null) {
            ownerName = acc.getLecturer().getFullName();
        }

        // 4. Lấy các ID cần thiết để phục vụ phân quyền và nạp dữ liệu cá nhân
        Long studentId = (acc.getStudent() != null) ? acc.getStudent().getId() : null;
        Long lecturerId = (acc.getLecturer() != null) ? acc.getLecturer().getId() : null;

        // 5. TẠO DTO VỚI ĐẦY ĐỦ 6 THAM SỐ (Fix lỗi compile ở đây)
        AccountDTO dto = new AccountDTO(
                acc.getId(),
                acc.getUsername(),
                acc.getRole(),
                ownerName,
                studentId,
                lecturerId
        );

        // 6. Lưu vào phiên làm việc (Session)
        SessionManager.login(dto);

        return "SUCCESS";
    }

    /**
     * HÀM TẠO TÀI KHOẢN (Dành cho Admin)
     */
    public void createAccount(Account account) {
        // LUÔN LUÔN băm mật khẩu trước khi lưu để bảo mật
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            String hashedPassword = PasswordUtil.hashPassword(account.getPassword());
            account.setPassword(hashedPassword);
        }

        accountDao.save(account);
    }

    public void logout() {
        SessionManager.logout();
    }
}