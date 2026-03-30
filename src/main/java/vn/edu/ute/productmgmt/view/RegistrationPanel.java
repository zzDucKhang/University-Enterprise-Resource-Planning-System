package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistrationPanel extends JPanel {
    private JTable tableClasses;
    private DefaultTableModel tableModel;
    private JButton btnRegister, btnRefresh;

    public RegistrationPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblHeader = new JLabel("DANH SÁCH LỚP HỌC PHẦN ĐANG MỞ", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblHeader, BorderLayout.NORTH);

        String[] cols = {"ID", "Mã lớp", "Môn học", "Tín chỉ", "Giảng viên", "Lịch học", "Sĩ số"};
<<<<<<< HEAD
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableClasses = new JTable(tableModel);
        tableClasses.getColumnModel().getColumn(0).setMinWidth(0);
        tableClasses.getColumnModel().getColumn(0).setMaxWidth(0);
        tableClasses.getColumnModel().getColumn(0).setPreferredWidth(0);
=======
        tableModel = new DefaultTableModel(cols, 0);
        tableClasses = new JTable(tableModel);

        // Ẩn cột ID
        tableClasses.getColumnModel().getColumn(0).setMinWidth(0);
        tableClasses.getColumnModel().getColumn(0).setMaxWidth(0);

>>>>>>> e42212cd2ac4d13cca7404df0da395bf07b0f515
        add(new JScrollPane(tableClasses), BorderLayout.CENTER);

        JPanel pnlSouth = new JPanel();
        btnRegister = new JButton("Đăng ký học phần đã chọn");
        btnRefresh = new JButton("Tải lại danh sách");
        pnlSouth.add(btnRegister);
        pnlSouth.add(btnRefresh);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    public JTable getTableClasses() { return tableClasses; }
    public JButton getBtnRegister() { return btnRegister; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
