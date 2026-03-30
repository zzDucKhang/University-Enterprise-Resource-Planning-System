package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;
import vn.edu.ute.productmgmt.model.entity.ClassSection;
import vn.edu.ute.productmgmt.model.entity.Course;
import vn.edu.ute.productmgmt.model.entity.Lecturer;
import vn.edu.ute.productmgmt.model.entity.Semester;
import vn.edu.ute.productmgmt.model.enums.StudyDay;
import vn.edu.ute.productmgmt.model.util.MessageUtil;
import vn.edu.ute.productmgmt.service.ClassSectionService;
import vn.edu.ute.productmgmt.service.CourseService;
import vn.edu.ute.productmgmt.service.LecturerService;
import vn.edu.ute.productmgmt.service.SemesterService;
import vn.edu.ute.productmgmt.view.ClassAdminPanel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ClassAdminController {
    private ClassAdminPanel view;
    private ClassSectionService classSectionService;
    private CourseService courseService;
    private LecturerService lecturerService;
    private SemesterService semesterService;
    
    private List<Course> courses;
    private List<Lecturer> lecturers;
    private List<Semester> semesters;

    public ClassAdminController(ClassAdminPanel view) {
        this.view = view;
        this.classSectionService = new ClassSectionService();
        this.courseService = new CourseService();
        this.lecturerService = new LecturerService();
        this.semesterService = new SemesterService();

        initEvents();
        loadComboBoxes();
        loadData();
    }

    private void initEvents() {
        view.getBtnAdd().addActionListener(e -> handleAdd());
        view.getBtnUpdate().addActionListener(e -> handleUpdate());
        view.getBtnDelete().addActionListener(e -> handleDelete());
        view.getBtnClear().addActionListener(e -> clearForm());

        view.getTableClasses().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) mapTableToForm();
        });
    }

    private void loadComboBoxes() {
        // Load Courses
        courses = courseService.getAllWithPrerequisites();
        view.getCbCourse().removeAllItems();
        for (Course c : courses) view.getCbCourse().addItem(c.getTitle());

        // Load Semesters
        semesters = semesterService.getAll();
        view.getCbSemester().removeAllItems();
        for (Semester s : semesters) view.getCbSemester().addItem(s.getSemesterName());

        // Load Lecturers
        lecturers = lecturerService.getAll();
        view.getCbLecturer().removeAllItems();
        for (Lecturer l : lecturers) view.getCbLecturer().addItem(l.getFullName());
    }

    public void loadData() {
        List<ClassSectionDTO> classes = classSectionService.getAllClassSectionDTOs();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (ClassSectionDTO c : classes) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getClassCode(),
                    c.getCourseName(),
                    c.getLecturerName(),
                    c.getSemesterName(),
                    c.getRoom(),
                    c.getScheduleDisplay(),
                    c.getCapacityDisplay()
            });
        }
    }

    private void handleAdd() {
        if (courses.isEmpty() || semesters.isEmpty() || lecturers.isEmpty()) {
            MessageUtil.showError(view, "Thiếu dữ liệu tĩnh (Môn học/Giảng viên/Học kỳ) để tạo Lớp!");
            return;
        }

        try {
            ClassSection cs = new ClassSection();
            cs.setClassCode(view.getTxtClassCode().getText());
            cs.setRoom(view.getTxtRoom().getText());
            cs.setStartPeriod(Integer.parseInt(view.getTxtStartPeriod().getText()));
            cs.setEndPeriod(Integer.parseInt(view.getTxtEndPeriod().getText()));
            cs.setMaxCapacity(Integer.parseInt(view.getTxtMaxCapacity().getText()));
            
            cs.setDayOfWeek((StudyDay) view.getCbDayOfWeek().getSelectedItem());
            cs.setCourse(courses.get(view.getCbCourse().getSelectedIndex()));
            cs.setSemester(semesters.get(view.getCbSemester().getSelectedIndex()));
            cs.setLecturer(lecturers.get(view.getCbLecturer().getSelectedIndex()));

            String res = classSectionService.saveClassSection(cs);
            if ("SUCCESS".equals(res)) {
                MessageUtil.showInfo(view, "Thêm Lớp học phần thành công!");
                clearForm();
            } else {
                MessageUtil.showError(view, res);
            }
        } catch (NumberFormatException ex) {
            MessageUtil.showError(view, "Tiết học và Sĩ số phải là số nguyên!");
        }
    }

    private void handleUpdate() {
        int row = view.getTableClasses().getSelectedRow();
        if (row == -1) {
            MessageUtil.showError(view, "Vui lòng chọn lớp học phần cần sửa!");
            return;
        }

        Long id = (Long) view.getTableClasses().getValueAt(row, 0);
        ClassSection cs = classSectionService.findById(id);

        if (cs != null) {
            try {
                cs.setClassCode(view.getTxtClassCode().getText()); // Thường mã lớp không đổi, nhưng cứ cho đổi
                cs.setRoom(view.getTxtRoom().getText());
                cs.setStartPeriod(Integer.parseInt(view.getTxtStartPeriod().getText()));
                cs.setEndPeriod(Integer.parseInt(view.getTxtEndPeriod().getText()));
                cs.setMaxCapacity(Integer.parseInt(view.getTxtMaxCapacity().getText()));
                
                cs.setDayOfWeek((StudyDay) view.getCbDayOfWeek().getSelectedItem());
                cs.setCourse(courses.get(view.getCbCourse().getSelectedIndex()));
                cs.setSemester(semesters.get(view.getCbSemester().getSelectedIndex()));
                cs.setLecturer(lecturers.get(view.getCbLecturer().getSelectedIndex()));

                String res = classSectionService.saveClassSection(cs);
                if ("SUCCESS".equals(res)) {
                    MessageUtil.showInfo(view, "Cập nhật Lớp học phần thành công!");
                    clearForm();
                } else {
                    MessageUtil.showError(view, res);
                }
            } catch (NumberFormatException ex) {
                MessageUtil.showError(view, "Tiết học và Sĩ số phải là số nguyên!");
            }
        }
    }

    private void handleDelete() {
        int row = view.getTableClasses().getSelectedRow();
        if (row != -1) {
            Long id = (Long) view.getTableClasses().getValueAt(row, 0);
            String code = view.getTableClasses().getValueAt(row, 1).toString();
            
            if (MessageUtil.showConfirm(view, "Xóa Lớp " + code + "? (Các đăng ký của SV liên quan cũng có thể bị ảnh hưởng)")) {
                try {
                    classSectionService.deleteClassSection(id);
                    MessageUtil.showInfo(view, "Xóa thành công!");
                    clearForm();
                } catch (Exception ex) {
                    MessageUtil.showError(view, "Không thể xóa lớp này: " + ex.getMessage());
                }
            }
        } else {
            MessageUtil.showError(view, "Vui lòng chọn Lớp học phần cần xóa trên bảng!");
        }
    }

    private void clearForm() {
        view.getTxtClassCode().setText("");
        view.getTxtRoom().setText("");
        view.getTxtStartPeriod().setText("");
        view.getTxtEndPeriod().setText("");
        view.getTxtMaxCapacity().setText("");

        if (view.getCbCourse().getItemCount() > 0) view.getCbCourse().setSelectedIndex(0);
        if (view.getCbSemester().getItemCount() > 0) view.getCbSemester().setSelectedIndex(0);
        if (view.getCbLecturer().getItemCount() > 0) view.getCbLecturer().setSelectedIndex(0);
        view.getCbDayOfWeek().setSelectedIndex(0);

        view.getTableClasses().clearSelection();
        loadData();
    }

    private void mapTableToForm() {
        int row = view.getTableClasses().getSelectedRow();
        if (row != -1) {
            Long id = (Long) view.getTableClasses().getValueAt(row, 0);
            ClassSection cs = classSectionService.findById(id);

            if (cs != null) {
                view.getTxtClassCode().setText(cs.getClassCode());
                view.getTxtRoom().setText(cs.getRoom());
                view.getTxtStartPeriod().setText(String.valueOf(cs.getStartPeriod()));
                view.getTxtEndPeriod().setText(String.valueOf(cs.getEndPeriod()));
                view.getTxtMaxCapacity().setText(String.valueOf(cs.getMaxCapacity()));

                // Map comboboxes
                view.getCbDayOfWeek().setSelectedItem(cs.getDayOfWeek());
                if (cs.getCourse() != null) view.getCbCourse().setSelectedItem(cs.getCourse().getTitle());
                if (cs.getSemester() != null) view.getCbSemester().setSelectedItem(cs.getSemester().getSemesterName());
                if (cs.getLecturer() != null) view.getCbLecturer().setSelectedItem(cs.getLecturer().getFullName());
            }
        }
    }
}
