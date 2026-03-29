package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentTranscriptPanel extends JPanel {
    private JTable tableTranscript;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    
    // Các nhãn thống kê
    private JLabel lblTotalCredits;
    private JLabel lblGpa10;
    private JLabel lblGpa4;

    public StudentTranscriptPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Panel Tiêu đề & Nút thao tác
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("KẾT QUẢ HỌC TẬP (BẢNG ĐIỂM)", JLabel.LEFT);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        
        btnRefresh = new JButton("Làm mới kết quả");
        
        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(btnRefresh, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 2. Bảng Danh sách môn học
        String[] columns = {"Mã Môn Học", "Tên Môn Học", "Số Tín Chỉ", "Điểm Số", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Chỉ xem, không được sửa điểm trực tiếp trên bảng
            }
        };
        
        tableTranscript = new JTable(tableModel);
        tableTranscript.setRowHeight(25);
        tableTranscript.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Chỉnh cột cho đẹp
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableTranscript.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableTranscript.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableTranscript.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableTranscript.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableTranscript.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(tableTranscript);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Panel Thống kê cá nhân (Dưới cùng)
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Tổng Kết Học Tập Cuối Kỳ"));
        
        lblTotalCredits = new JLabel("Tổng số tín chỉ hoàn thành: 0");
        lblTotalCredits.setFont(new Font("Arial", Font.BOLD, 13));
        
        lblGpa10 = new JLabel("Điểm TB Hệ 10: 0.00");
        lblGpa10.setFont(new Font("Arial", Font.BOLD, 13));
        
        lblGpa4 = new JLabel("Điểm TB Hệ 4: 0.00");
        lblGpa4.setFont(new Font("Arial", Font.BOLD, 13));
        lblGpa4.setForeground(Color.RED);
        
        bottomPanel.add(lblTotalCredits);
        bottomPanel.add(lblGpa10);
        bottomPanel.add(lblGpa4);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JTable getTableTranscript() { return tableTranscript; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JLabel getLblTotalCredits() { return lblTotalCredits; }
    public JLabel getLblGpa10() { return lblGpa10; }
    public JLabel getLblGpa4() { return lblGpa4; }
}
