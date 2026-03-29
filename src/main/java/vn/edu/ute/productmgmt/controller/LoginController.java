package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.service.AccountService;
import vn.edu.ute.productmgmt.view.LoginView;
import vn.edu.ute.productmgmt.view.MainApp;
import vn.edu.ute.productmgmt.model.util.MessageUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView view;
    private AccountService accountService;

    public LoginController(LoginView view) {
        this.view = view;
        this.accountService = new AccountService();

        // Gắn sự kiện cho nút Đăng nhập
        this.view.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Nút Thoát
        this.view.getBtnExit().addActionListener(e -> System.exit(0));
    }

    private void handleLogin() {
        String username = view.getTxtUsername().getText();
        String password = new String(view.getTxtPassword().getPassword());

        String result = accountService.login(username, password);

        if (result.equals("SUCCESS")) {
            // 1. Lấy thông tin tài khoản vừa đăng nhập thành công từ Session
            vn.edu.ute.productmgmt.model.dto.AccountDTO currentAcc =
                    vn.edu.ute.productmgmt.model.util.SessionManager.getCurrentAccount();

            // 2. TRUYỀN ROLE VÀO ĐÂY (Sửa lỗi Build Failed)
            vn.edu.ute.productmgmt.view.MainApp mainApp = new vn.edu.ute.productmgmt.view.MainApp(currentAcc.getRole());

            // 3. Khởi tạo Controller chính
            new vn.edu.ute.productmgmt.controller.MainController(mainApp);

            // 4. Hiển thị màn hình chính và đóng màn hình Login
            mainApp.setVisible(true);
            view.dispose();

        } else {
            vn.edu.ute.productmgmt.model.util.MessageUtil.showError(view, result);
        }
    }
}