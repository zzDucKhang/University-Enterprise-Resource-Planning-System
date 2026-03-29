package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.service.StudentService;
import vn.edu.ute.productmgmt.service.ClassSectionService;
import vn.edu.ute.productmgmt.view.StatisticPanel;

import javax.swing.table.DefaultTableModel;

public class StatisticController {
    private StatisticPanel view;
    private StudentService studentService;
    private ClassSectionService classService;

    public StatisticController(StatisticPanel view) {
        this.view = view;
        this.studentService = new StudentService();
        this.classService = new ClassSectionService();
        refreshStats();
    }

    public void refreshStats() {
        view.getLblTotalStudents().setText("Tổng số: " + studentService.countTotal());
        view.getLblTotalGroups().setText("Đang mở: " + classService.countTotal());

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        int rank = 1;
        for (vn.edu.ute.productmgmt.model.dto.StudentDTO s : studentService.getTop10StudentsByGPA()) {
            model.addRow(new Object[]{
                    rank++,
                    s.getStudentCode(),
                    s.getFullName(),
                    s.getGpaFormatted()
            });
        }
    }
}