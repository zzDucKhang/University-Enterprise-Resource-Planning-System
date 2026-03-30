package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import vn.edu.ute.productmgmt.model.enums.StudyDay;

public class ClassAdminPanel extends JPanel {
    private JTextField txtClassCode, txtRoom, txtStartPeriod, txtEndPeriod, txtMaxCapacity;
    private JComboBox<String> cbCourse;
    private JComboBox<String> cbSemester;
    private JComboBox<String> cbLecturer;
    private JComboBox<StudyDay> cbDayOfWeek;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable tableClasses;
    private DefaultTableModel tableModel;

    public ClassAdminPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // --- INPUT SECTION ---
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(new TitledBorder("Thông tin Lớp học phần"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; pnlInput.add(new JLabel("Mã MH:"), g);
        g.gridx = 1; 
        cbCourse = new JComboBox<>();
        pnlInput.add(cbCourse, g);

        g.gridx = 2; pnlInput.add(new JLabel("Mã Lớp:"), g);
        g.gridx = 3; 
        txtClassCode = new JTextField(15);
        pnlInput.add(txtClassCode, g);

        g.gridx = 0; g.gridy = 1; pnlInput.add(new JLabel("Học kỳ:"), g);
        g.gridx = 1; 
        cbSemester = new JComboBox<>();
        pnlInput.add(cbSemester, g);

        g.gridx = 2; pnlInput.add(new JLabel("Phòng:"), g);
        g.gridx = 3; 
        txtRoom = new JTextField(15);
        pnlInput.add(txtRoom, g);

        g.gridx = 0; g.gridy = 2; pnlInput.add(new JLabel("Giảng viên:"), g);
        g.gridx = 1; 
        cbLecturer = new JComboBox<>();
        pnlInput.add(cbLecturer, g);

        g.gridx = 2; pnlInput.add(new JLabel("Thứ:"), g);
        g.gridx = 3; 
        cbDayOfWeek = new JComboBox<>(StudyDay.values());
        pnlInput.add(cbDayOfWeek, g);

        g.gridx = 0; g.gridy = 3; pnlInput.add(new JLabel("Tiết BĐ / KT:"), g);
        g.gridx = 1; 
        JPanel pnlPeriod = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        txtStartPeriod = new JTextField(5);
        pnlPeriod.add(txtStartPeriod);
        pnlPeriod.add(new JLabel(" - "));
        txtEndPeriod = new JTextField(5);
        pnlPeriod.add(txtEndPeriod);
        pnlInput.add(pnlPeriod, g);

        g.gridx = 2; pnlInput.add(new JLabel("Sĩ số Tối đa:"), g);
        g.gridx = 3; 
        txtMaxCapacity = new JTextField(10);
        pnlInput.add(txtMaxCapacity, g);

        // --- ACTIONS SECTION ---
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");

        pnlActions.add(btnAdd);
        pnlActions.add(btnUpdate);
        pnlActions.add(btnDelete);
        pnlActions.add(btnClear);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlInput, BorderLayout.CENTER);
        pnlNorth.add(pnlActions, BorderLayout.SOUTH);

        // --- TABLE SECTION ---
        String[] headers = {"ID", "Mã Lớp", "Tên Môn", "Giảng viên", "Học kỳ", "Phòng", "Lịch học", "Sĩ số TĐ"};
        tableModel = new DefaultTableModel(headers, 0);
        tableClasses = new JTable(tableModel);
        
        // Ẩn cột ID
        tableClasses.getColumnModel().getColumn(0).setMinWidth(0);
        tableClasses.getColumnModel().getColumn(0).setMaxWidth(0);

        add(pnlNorth, BorderLayout.NORTH);
        add(new JScrollPane(tableClasses), BorderLayout.CENTER);
    }

    // Getters
    public JTextField getTxtClassCode() { return txtClassCode; }
    public JTextField getTxtRoom() { return txtRoom; }
    public JTextField getTxtStartPeriod() { return txtStartPeriod; }
    public JTextField getTxtEndPeriod() { return txtEndPeriod; }
    public JTextField getTxtMaxCapacity() { return txtMaxCapacity; }
    public JComboBox<String> getCbCourse() { return cbCourse; }
    public JComboBox<String> getCbSemester() { return cbSemester; }
    public JComboBox<String> getCbLecturer() { return cbLecturer; }
    public JComboBox<StudyDay> getCbDayOfWeek() { return cbDayOfWeek; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTable getTableClasses() { return tableClasses; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
