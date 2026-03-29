package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.service.StudentService;
import vn.edu.ute.productmgmt.service.ClassSectionService;
import vn.edu.ute.productmgmt.view.StatisticPanel;

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

    // Đổi thành PUBLIC để MainController gọi được
    public void refreshStats() {
        view.getLblTotalStudents().setText("Tổng số: " + studentService.countTotal());
        view.getLblTotalGroups().setText("Đang mở: " + classService.countTotal());
    }
}