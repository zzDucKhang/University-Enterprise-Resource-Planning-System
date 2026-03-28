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
        String user = view.getTxtUsername().getText();
        String pass = new String(view.getTxtPassword().getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            MessageUtil.showError(view, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        String result = accountService.login(user, pass);
        if (result.equals("SUCCESS")) {
            MessageUtil.showInfo(view, "Đăng nhập thành công!");
            view.dispose(); // Đóng màn hình Login

            // Khởi chạy giao diện chính và Controller chính
            MainApp mainApp = new MainApp();
            new MainController(mainApp);
            mainApp.setVisible(true);
        } else {
            MessageUtil.showError(view, result);
        }
    }
}