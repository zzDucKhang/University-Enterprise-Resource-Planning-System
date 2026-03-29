package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.dto.StudentDTO;
import vn.edu.ute.productmgmt.model.entity.Major;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.enums.Gender;
import vn.edu.ute.productmgmt.model.util.MessageUtil;
import vn.edu.ute.productmgmt.service.MajorService;
import vn.edu.ute.productmgmt.service.StudentService;
import vn.edu.ute.productmgmt.view.StudentPanel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentController {
    private StudentPanel view;
    private StudentService studentService;
    private MajorService majorService;
    private MainController mainController; // Kết nối với sếp tổng
    private List<Major> listMajors;

    public StudentController(StudentPanel view, MainController mainController) {
        this.view = view;
        this.mainController = mainController;
        this.studentService = new StudentService();
        this.majorService = new MajorService();

        initEvents();
        loadMajorsToCombo();
        loadData();
    }

    private void initEvents() {
        view.getBtnAdd().addActionListener(e -> handleAdd());
        view.getBtnUpdate().addActionListener(e -> handleUpdate());
        view.getBtnDelete().addActionListener(e -> handleDelete());
        view.getBtnClear().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> handleSearch());

        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) mapTableToForm();
        });
    }

    public void loadData() { // Để public để MainController gọi được
        List<StudentDTO> students = studentService.getAllStudentDTOs();
        fillTable(students);
    }

    private void fillTable(List<StudentDTO> list) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (StudentDTO s : list) {
            model.addRow(new Object[]{s.getStudentCode(), s.getFullName(), s.getEmail(), s.getGender(), s.getMajorName(), s.getGpaFormatted()});
        }
    }

    private void handleAdd() {
        Student s = new Student();
        s.setStudentCode(view.getTxtCode().getText());
        s.setFullName(view.getTxtName().getText());
        s.setEmail(view.getTxtEmail().getText());
        s.setGender(Gender.valueOf(view.getCbGender().getSelectedItem().toString()));

        String selectedMajorName = view.getCbMajor().getSelectedItem().toString();
        Major major = listMajors.stream().filter(m -> m.getName().equals(selectedMajorName)).findFirst().orElse(null);
        s.setMajor(major);

        String res = studentService.saveStudent(s);
        if (res.equals("SUCCESS")) {
            MessageUtil.showInfo(view, "Thêm thành công!");
            mainController.refreshAllData(); // RA LỆNH LÀM MỚI TOÀN CỤC
            clearForm();
        } else {
            MessageUtil.showError(view, res);
        }
    }

    private void handleUpdate() {
        Student s = studentService.findByCode(view.getTxtCode().getText());
        if (s != null) {
            s.setFullName(view.getTxtName().getText());
            s.setEmail(view.getTxtEmail().getText());
            s.setGender(Gender.valueOf(view.getCbGender().getSelectedItem().toString()));

            String selectedMajorName = view.getCbMajor().getSelectedItem().toString();
            Major major = listMajors.stream().filter(m -> m.getName().equals(selectedMajorName)).findFirst().orElse(null);
            s.setMajor(major);

            studentService.saveStudent(s);
            MessageUtil.showInfo(view, "Cập nhật thành công!");
            mainController.refreshAllData(); // RA LỆNH LÀM MỚI TOÀN CỤC
            clearForm();
        }
    }

    private void handleDelete() {
        int row = view.getTable().getSelectedRow();
        if (row != -1) {
            String code = view.getTable().getValueAt(row, 0).toString();
            if (MessageUtil.showConfirm(view, "Xóa SV " + code + "?")) {
                studentService.deleteStudent(studentService.findByCode(code).getId());
                mainController.refreshAllData(); // RA LỆNH LÀM MỚI TOÀN CỤC
                clearForm();
            }
        }
    }

    private void handleSearch() {
        fillTable(studentService.search(view.getTxtSearch().getText()));
    }

    private void loadMajorsToCombo() {
        listMajors = majorService.getAllMajors();
        view.getCbMajor().removeAllItems();
        for (Major m : listMajors) view.getCbMajor().addItem(m.getName());
    }

    private void clearForm() {
        view.getTxtCode().setEditable(true);
        view.getTxtCode().setText("");
        view.getTxtName().setText("");
        view.getTxtEmail().setText("");
        view.getTxtSearch().setText("");
        if (view.getCbMajor().getItemCount() > 0) view.getCbMajor().setSelectedIndex(0);
        view.getTable().clearSelection();
        loadData();
    }

    private void mapTableToForm() {
        int row = view.getTable().getSelectedRow();
        if (row != -1) {
            view.getTxtCode().setText(view.getTable().getValueAt(row, 0).toString());
            view.getTxtCode().setEditable(false);
            view.getTxtName().setText(view.getTable().getValueAt(row, 1).toString());
            view.getTxtEmail().setText(view.getTable().getValueAt(row, 2).toString());
            view.getCbGender().setSelectedItem(view.getTable().getValueAt(row, 3).toString());
            Object majorObj = view.getTable().getValueAt(row, 4);
            if (majorObj != null) view.getCbMajor().setSelectedItem(majorObj.toString());
        }
    }
}