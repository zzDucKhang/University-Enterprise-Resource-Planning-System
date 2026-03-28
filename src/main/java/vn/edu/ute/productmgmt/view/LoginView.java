package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;

    public LoginView() {
        setTitle("Đăng nhập hệ thống - UTE ERP");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ ĐÀO TẠO", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 1;
        panel.add(new JLabel("Tài khoản:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        // Buttons
        JPanel pnlButtons = new JPanel();
        btnLogin = new JButton("Đăng nhập");
        btnExit = new JButton("Thoát");
        pnlButtons.add(btnLogin);
        pnlButtons.add(btnExit);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(pnlButtons, gbc);

        add(panel);
    }

    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnExit() { return btnExit; }
}