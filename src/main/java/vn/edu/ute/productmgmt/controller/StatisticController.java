package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.dto.StudentDTO;
import vn.edu.ute.productmgmt.service.StudentService;
import vn.edu.ute.productmgmt.service.ClassSectionService;
import vn.edu.ute.productmgmt.view.StatisticPanel;

import javax.swing.table.DefaultTableModel;
<<<<<<< HEAD
import java.util.Comparator;
import java.util.List;
=======
>>>>>>> e42212cd2ac4d13cca7404df0da395bf07b0f515

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
<<<<<<< HEAD
        loadTopStudentsByGpa();
=======

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
>>>>>>> e42212cd2ac4d13cca7404df0da395bf07b0f515
    }

    private void loadTopStudentsByGpa() {
        List<StudentDTO> students = studentService.getAllStudentDTOs();
        students.sort(
                Comparator.comparing(
                                (StudentDTO s) -> s.getGpa() != null ? s.getGpa() : 0.0
                        )
                        .reversed()
                        .thenComparing(StudentDTO::getStudentCode)
        );

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        int limit = Math.min(10, students.size());
        for (int i = 0; i < limit; i++) {
            StudentDTO s = students.get(i);
            model.addRow(new Object[]{
                    i + 1,
                    s.getStudentCode(),
                    s.getFullName(),
                    s.getGpaFormatted()
            });
        }
    }
}
