package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;

public class GradePanel extends JPanel {
    private JComboBox<ClassSectionDTO> cbClasses;
    private JTable tableStudents;
    private DefaultTableModel tableModel;
    private JTextField txtScore;
    private JButton btnSave;

    public GradePanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // --- PHẦN BẮT GIAO DIỆN CHỌN LỚP ---
        JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlNorth.setBorder(new TitledBorder("Lựa chọn Lớp Học Phần"));
        pnlNorth.add(new JLabel("Lớp đang dạy:"));
        cbClasses = new JComboBox<>();
        cbClasses.setPreferredSize(new Dimension(500, 30));
        cbClasses.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ClassSectionDTO) {
                    ClassSectionDTO dto = (ClassSectionDTO) value;
                    setText(dto.getClassCode() + " - " + dto.getCourseName() + " (" + dto.getSemesterName() + ")");
                }
                return this;
            }
        });
        pnlNorth.add(cbClasses);
        add(pnlNorth, BorderLayout.NORTH);

        // --- BẢNG DANH SÁCH SINH VIÊN ---
        String[] headers = {"EnrollmentID", "MSSV", "Họ Tên", "Điểm", "Trạng Thái"};
        tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        tableStudents = new JTable(tableModel);
        
        // Ẩn cột EnrollmentID
        tableStudents.getColumnModel().getColumn(0).setMinWidth(0);
        tableStudents.getColumnModel().getColumn(0).setMaxWidth(0);
        
        add(new JScrollPane(tableStudents), BorderLayout.CENTER);

        // --- PHẦN NHẬP ĐIỂM ---
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSouth.setBorder(new TitledBorder("Cập nhật Điểm số"));
        pnlSouth.add(new JLabel("Điểm hệ 10:"));
        txtScore = new JTextField(10);
        pnlSouth.add(txtScore);
        
        btnSave = new JButton("Lưu Điểm");
        pnlSouth.add(btnSave);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    public JComboBox<ClassSectionDTO> getCbClasses() { return cbClasses; }
    public JTable getTableStudents() { return tableStudents; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTextField getTxtScore() { return txtScore; }
    public JButton getBtnSave() { return btnSave; }
}
