package vn.edu.ute.productmgmt;
import vn.edu.ute.productmgmt.controller.LoginController;
import vn.edu.ute.productmgmt.model.util.DatabaseSeeder;
import vn.edu.ute.productmgmt.view.LoginView;

import javax.swing.SwingUtilities;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        DatabaseSeeder.seed();
        // KHỞI CHẠY GIAO DIỆN
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }
}