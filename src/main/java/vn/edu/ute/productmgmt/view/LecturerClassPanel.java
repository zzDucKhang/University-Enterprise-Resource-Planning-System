package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LecturerClassPanel extends JPanel {
    private JTable tableClasses;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;

    public LecturerClassPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // Tiêu đề
        JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlNorth.setBorder(new TitledBorder("Danh sách lớp đang dạy"));
        btnRefresh = new JButton("Làm mới danh sách");
        pnlNorth.add(btnRefresh);

        // Bảng dữ liệu
        String[] headers = {"ID", "Mã Lớp", "Tên Môn", "Phòng", "Lịch học", "Sĩ số"};
        tableModel = new DefaultTableModel(headers, 0);
        tableClasses = new JTable(tableModel);

        // Ẩn cột ID cho chuyên nghiệp
        tableClasses.getColumnModel().getColumn(0).setMinWidth(0);
        tableClasses.getColumnModel().getColumn(0).setMaxWidth(0);

        add(pnlNorth, BorderLayout.NORTH);
        add(new JScrollPane(tableClasses), BorderLayout.CENTER);
    }

    public JTable getTableClasses() { return tableClasses; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
}