package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StatisticPanel extends JPanel {
    private JLabel lblTotalStudents, lblTotalGroups;
    private JTable tableTopStudents;
    private DefaultTableModel tableModel;

    public StatisticPanel() {
        setLayout(new BorderLayout(15, 15));

        // Thẻ tổng quan (Cards)
        JPanel pnlSummary = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel card1 = new JPanel(); card1.setBorder(new TitledBorder("Sinh viên"));
        lblTotalStudents = new JLabel("Tổng số: 0", JLabel.CENTER);
        lblTotalStudents.setFont(new Font("Arial", Font.BOLD, 24));
        card1.add(lblTotalStudents);

        JPanel card2 = new JPanel(); card2.setBorder(new TitledBorder("Lớp học phần"));
        lblTotalGroups = new JLabel("Đang mở: 0", JLabel.CENTER);
        lblTotalGroups.setFont(new Font("Arial", Font.BOLD, 24));
        card2.add(lblTotalGroups);

        pnlSummary.add(card1);
        pnlSummary.add(card2);

        // Bảng Top GPA
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(new TitledBorder("Top 10 Sinh viên có GPA cao nhất"));
        tableModel = new DefaultTableModel(new String[]{"Hạng", "MSSV", "Họ Tên", "GPA"}, 0);
        tableTopStudents = new JTable(tableModel);
        pnlTable.add(new JScrollPane(tableTopStudents), BorderLayout.CENTER);

        add(pnlSummary, BorderLayout.NORTH);
        add(pnlTable, BorderLayout.CENTER);
    }

    public JLabel getLblTotalStudents() { return lblTotalStudents; }
    public JLabel getLblTotalGroups() { return lblTotalGroups; }
    public DefaultTableModel getTableModel() { return tableModel; }
}