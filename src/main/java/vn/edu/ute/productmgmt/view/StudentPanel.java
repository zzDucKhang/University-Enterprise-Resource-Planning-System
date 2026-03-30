package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentPanel extends JPanel {
    private JTextField txtCode, txtName, txtEmail, txtSearch;
    private JComboBox<String> cbGender;
    private JComboBox<String> cbMajor;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;


    public StudentPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // --- PHẦN TRÊN: NHẬP LIỆU ---
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(new TitledBorder("Thông tin chi tiết"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5); g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; pnlInput.add(new JLabel("MSSV:"), g);
        g.gridx = 1; txtCode = new PlaceholderTextField(15, "Nhập mã sinh viên..."); pnlInput.add(txtCode, g);

        g.gridx = 2; pnlInput.add(new JLabel("Họ tên:"), g);
        g.gridx = 3; txtName = new JTextField(20); pnlInput.add(txtName, g);

        g.gridx = 0; g.gridy = 1; pnlInput.add(new JLabel("Email:"), g);
        g.gridx = 1; txtEmail = new JTextField(15); pnlInput.add(txtEmail, g);

        g.gridx = 2; pnlInput.add(new JLabel("Giới tính:"), g);
        g.gridx = 3; cbGender = new JComboBox<>(new String[]{"MALE", "FEMALE", "OTHER"}); pnlInput.add(cbGender, g);

        g.gridx = 0; g.gridy = 2; pnlInput.add(new JLabel("Ngành học:"), g);
        g.gridx = 1; g.gridwidth = 3; // Cho ô chọn Ngành dài ra
        cbMajor = new JComboBox<>();
        pnlInput.add(cbMajor, g);
        g.gridwidth = 1; // Reset lại gridwidth

        // --- PHẦN GIỮA: NÚT & TÌM KIẾM ---
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");

        txtSearch = new PlaceholderTextField(20, "Nhập MSSV hoặc tên sinh viên để tìm...");
        btnSearch = new JButton("Tìm kiếm");

        pnlActions.add(btnAdd); pnlActions.add(btnUpdate); pnlActions.add(btnDelete); pnlActions.add(btnClear);
        pnlActions.add(new JLabel("  |  Tìm kiếm:")); pnlActions.add(txtSearch); pnlActions.add(btnSearch);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlInput, BorderLayout.CENTER);
        pnlNorth.add(pnlActions, BorderLayout.SOUTH);

        // --- PHẦN DƯỚI: BẢNG ---
        String[] headers = {"MSSV", "Họ Tên", "Email", "Giới Tính", "Ngành", "GPA"};
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);

        add(pnlNorth, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // Getters
    public JTextField getTxtCode() { return txtCode; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JComboBox<String> getCbGender() { return cbGender; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JComboBox<String> getCbMajor() { return cbMajor; }

    // Custom class tạo chữ mờ (Placeholder) an toàn
    class PlaceholderTextField extends JTextField {
        private String placeholder;
        
        public PlaceholderTextField(int columns, String placeholder) {
            super(columns);
            this.placeholder = placeholder;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.GRAY);
                g2.setFont(getFont().deriveFont(Font.ITALIC));
                int x = getInsets().left;
                int y = (getHeight() - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent();
                g2.drawString(placeholder, x, y);
                g2.dispose();
            }
        }
    }
}