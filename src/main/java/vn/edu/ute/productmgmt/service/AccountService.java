package vn.edu.ute.productmgmt.service;
import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.entity.Lecturer;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import vn.edu.ute.productmgmt.model.dao.AccountDao;
import vn.edu.ute.productmgmt.model.dto.AccountDTO;
import vn.edu.ute.productmgmt.model.entity.Account;
import vn.edu.ute.productmgmt.model.util.PasswordUtil;
import vn.edu.ute.productmgmt.model.util.SessionManager;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();

    // Hàm login account, có phân quyền
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

     //HÀM TẠO TÀI KHOẢN (Dành cho Admin)
     public void createAccount(Account account) {
         EntityManager em = JpaUtil.getEntityManager();
         try {
             em.getTransaction().begin();

             // 1. Băm mật khẩu (Giữ nguyên logic cũ)
             if (account.getPassword() != null && !account.getPassword().isEmpty()) {
                 String hashedPassword = PasswordUtil.hashPassword(account.getPassword());
                 account.setPassword(hashedPassword);
             }

             // 2. KỸ THUẬT "NHẬN NGƯỜI QUEN" (Re-attach Entity)
             // Nếu là tài khoản Sinh viên, phải tìm lại Student trong Session hiện tại
             if (account.getStudent() != null && account.getStudent().getId() != null) {
                 Student managedStudent = em.find(Student.class, account.getStudent().getId());
                 account.setStudent(managedStudent);
             }

             // Tương tự nếu sau này tạo cho Giảng viên
             if (account.getLecturer() != null && account.getLecturer().getId() != null) {
                 Lecturer managedLecturer = em.find(Lecturer.class, account.getLecturer().getId());
                 account.setLecturer(managedLecturer);
             }

             // 3. Lưu trực tiếp bằng EntityManager của hàm này (Không gọi DAO nữa để tránh bị tách session)
             em.persist(account);

             em.getTransaction().commit();
             System.out.println(">>> Đã tạo tài khoản thành công cho: " + account.getUsername());

         } catch (Exception e) {
             if (em.getTransaction().isActive()) em.getTransaction().rollback();
             e.printStackTrace();
             throw new RuntimeException("Lỗi khi tạo Account: " + e.getMessage());
         } finally {
             em.close();
         }
     }

    public void logout() {
        SessionManager.logout();
    }
}