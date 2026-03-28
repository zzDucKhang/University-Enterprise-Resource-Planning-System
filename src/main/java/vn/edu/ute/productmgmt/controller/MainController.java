package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.view.MainApp;

public class MainController {
    private MainApp mainApp;

    public MainController(MainApp mainApp) {
        this.mainApp = mainApp;

        // Khởi tạo các Controller con cho từng Tab
        new StudentController(mainApp.getStudentPanel());
        new RegistrationController(mainApp.getRegistrationPanel());
        new StatisticController(mainApp.getStatisticPanel());

        // Xử lý sự kiện Menu (Ví dụ: Đăng xuất)
        initMenuEvents();
    }

    private void initMenuEvents() {
        // Logic đăng xuất có thể viết ở đây
    }
}