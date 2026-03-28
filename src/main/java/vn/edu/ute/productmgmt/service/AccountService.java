package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.AccountDao;
import vn.edu.ute.productmgmt.model.dto.AccountDTO;
import vn.edu.ute.productmgmt.model.entity.Account;
import vn.edu.ute.productmgmt.model.util.PasswordUtil;
import vn.edu.ute.productmgmt.model.util.SessionManager;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();

    public String login(String username, String password) {
        Account acc = accountDao.findByUsername(username);

        if (acc == null) {
            return "Tài khoản không tồn tại!";
        }

        if (!PasswordUtil.checkPassword(password, acc.getPassword())) {
            return "Mật khẩu không chính xác!";
        }

        // Nếu OK, tạo DTO và lưu vào Session
        String ownerName = (acc.getStudent() != null) ? acc.getStudent().getFullName() :
                (acc.getLecturer() != null) ? acc.getLecturer().getFullName() : "Admin";

        AccountDTO dto = new AccountDTO(acc.getUsername(), acc.getRole(), ownerName);
        SessionManager.login(dto);

        return "SUCCESS";
    }

    // HÀM TẠO TÀI KHOẢN (Sử dụng khi quản trị viên thêm User mới)
    public void createAccount(Account account) {
        // LUÔN LUÔN băm mật khẩu trước khi gọi DAO.save()
        String hashedPassword = PasswordUtil.hashPassword(account.getPassword());
        account.setPassword(hashedPassword);

        accountDao.save(account);
    }

    public void logout() {
        SessionManager.logout();
    }
}