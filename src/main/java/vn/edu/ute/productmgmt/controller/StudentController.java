package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.dto.StudentDTO;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.enums.Gender;
import vn.edu.ute.productmgmt.model.util.MessageUtil;
import vn.edu.ute.productmgmt.service.StudentService;
import vn.edu.ute.productmgmt.view.StudentPanel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentController {
    private StudentPanel view;
    private StudentService studentService;

    public StudentController(StudentPanel view) {
        this.view = view;
        this.studentService = new StudentService();

        initEvents();
        loadData();
    }

    private void initEvents() {
        view.getBtnAdd().addActionListener(e -> handleAdd());
        view.getBtnUpdate().addActionListener(e -> handleUpdate()); // Đã hết lỗi resolve
        view.getBtnDelete().addActionListener(e -> handleDelete());
        view.getBtnClear().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> handleSearch());

        // Thêm sự kiện click vào Table để đổ dữ liệu ngược lên Form
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mapTableToForm();
            }
        });
    }

    private void loadData() {
        List<StudentDTO> students = studentService.getAllStudentDTOs();
        fillTable(students);
    }

    private void fillTable(List<StudentDTO> list) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (StudentDTO s : list) {
            model.addRow(new Object[]{
                    s.getStudentCode(),
                    s.getFullName(),
                    s.getEmail(),
                    s.getGender(),
                    s.getMajorName(),
                    s.getGpaFormatted()
            });
        }
    }

    private void handleAdd() {
        Student s = new Student();
        s.setStudentCode(view.getTxtCode().getText());
        s.setFullName(view.getTxtName().getText());
        s.setEmail(view.getTxtEmail().getText());
        s.setGender(Gender.valueOf(view.getCbGender().getSelectedItem().toString()));

        String res = studentService.saveStudent(s);
        if (res.equals("SUCCESS")) {
            MessageUtil.showInfo(view, "Thêm thành công!");
            loadData();
            clearForm();
        } else {
            MessageUtil.showError(view, res);
        }
    }

    // --- FIX LỖI: CÀI ĐẶT HÀM UPDATE ---
    private void handleUpdate() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            MessageUtil.showError(view, "Vui lòng chọn sinh viên từ bảng để sửa!");
            return;
        }

        String studentCode = view.getTxtCode().getText();
        // Tìm sinh viên hiện tại trong DB để cập nhật
        Student s = studentService.findByCode(studentCode);

        if (s != null) {
            s.setFullName(view.getTxtName().getText());
            s.setEmail(view.getTxtEmail().getText());
            s.setGender(Gender.valueOf(view.getCbGender().getSelectedItem().toString()));

            String res = studentService.saveStudent(s);
            if (res.equals("SUCCESS")) {
                MessageUtil.showInfo(view, "Cập nhật thông tin thành công!");
                loadData();
                clearForm();
            } else {
                MessageUtil.showError(view, res);
            }
        }
    }

    private void handleDelete() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            MessageUtil.showError(view, "Vui lòng chọn 1 dòng để xóa!");
            return;
        }

        String studentCode = view.getTable().getValueAt(row, 0).toString();
        if (MessageUtil.showConfirm(view, "Xác nhận xóa sinh viên mã: " + studentCode + "?")) {
            Student s = studentService.findByCode(studentCode);
            if (s != null) {
                studentService.deleteStudent(s.getId());
                MessageUtil.showInfo(view, "Đã xóa sinh viên!");
                loadData();
                clearForm();
            }
        }
    }

    private void handleSearch() {
        String keyword = view.getTxtSearch().getText();
        // Gọi hàm search đã viết bằng JPQL ở bước trước
        List<StudentDTO> results = studentService.search(keyword);
        fillTable(results);
    }

    private void clearForm() {
        view.getTxtCode().setEditable(true); // Cho phép nhập lại mã khi thêm mới
        view.getTxtCode().setText("");
        view.getTxtName().setText("");
        view.getTxtEmail().setText("");
        view.getTxtSearch().setText("");
    }

    // Hàm tiện ích: Khi click vào dòng trong bảng, thông tin hiện lên các ô nhập
    private void mapTableToForm() {
        int row = view.getTable().getSelectedRow();
        if (row != -1) {
            view.getTxtCode().setText(view.getTable().getValueAt(row, 0).toString());
            view.getTxtCode().setEditable(false); // Khóa mã sinh viên không cho sửa (Primary Key)
            view.getTxtName().setText(view.getTable().getValueAt(row, 1).toString());
            view.getTxtEmail().setText(view.getTable().getValueAt(row, 2).toString());
            view.getCbGender().setSelectedItem(view.getTable().getValueAt(row, 3).toString());
        }
    }
}