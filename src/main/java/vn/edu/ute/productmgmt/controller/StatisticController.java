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

    private void refreshStats() {
        // Lấy tổng số SV từ JPQL COUNT
        long totalStudents = studentService.countTotal();
        view.getLblTotalStudents().setText("Tổng số: " + totalStudents);

        // Lấy tổng số lớp đang mở
        long totalClasses = classService.countTotal();
        view.getLblTotalGroups().setText("Đang mở: " + totalClasses);

        // Đổ dữ liệu Top 10 GPA vào bảng
        // fillTopStudentTable();
    }
}