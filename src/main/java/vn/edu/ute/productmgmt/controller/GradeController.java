package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.dto.AccountDTO;
import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;
import vn.edu.ute.productmgmt.model.dto.EnrollmentDTO;
import vn.edu.ute.productmgmt.model.util.MessageUtil;
import vn.edu.ute.productmgmt.model.util.SessionManager;
import vn.edu.ute.productmgmt.service.ClassSectionService;
import vn.edu.ute.productmgmt.service.TranscriptService;
import vn.edu.ute.productmgmt.view.GradePanel;

import java.awt.event.ItemEvent;
import java.util.List;

public class GradeController {
    private GradePanel view;
    private ClassSectionService classSectionService;
    private TranscriptService transcriptService;
    private Long currentLecturerId;

    public GradeController(GradePanel view) {
        this.view = view;
        this.classSectionService = new ClassSectionService();
        this.transcriptService = new TranscriptService();

        initLecturerSession();
        initEvents();
        loadClasses();
    }

    private void initLecturerSession() {
        AccountDTO acc = SessionManager.getCurrentAccount();
        if (acc != null && acc.getRole() == vn.edu.ute.productmgmt.model.enums.UserRole.LECTURER) {
            currentLecturerId = acc.getLecturerId();
        } else {
            currentLecturerId = null; // Quản trị viên thấy tất cả
        }
    }

    public void loadClasses() {
        List<ClassSectionDTO> classes;
        if (currentLecturerId != null) {
            classes = classSectionService.getClassesByLecturer(currentLecturerId);
        } else {
            classes = classSectionService.getAllClassSectionDTOs();
        }

        view.getCbClasses().removeAllItems();
        for (ClassSectionDTO c : classes) {
            view.getCbClasses().addItem(c);
        }
        
        if (!classes.isEmpty()) {
            loadStudentsByClass((ClassSectionDTO) view.getCbClasses().getSelectedItem());
        } else {
            view.getTableModel().setRowCount(0);
        }
    }

    private void loadStudentsByClass(ClassSectionDTO classSection) {
        view.getTableModel().setRowCount(0);
        if (classSection == null) return;

        List<EnrollmentDTO> students = transcriptService.getEnrollmentsByClass(classSection.getId());
        for (EnrollmentDTO s : students) {
            view.getTableModel().addRow(new Object[]{
                    s.getId(), // Cột ẩn ID
                    s.getStudentCode(),
                    s.getStudentName(),
                    s.getScore() != null ? s.getScore() : "Chưa có",
                    s.getStatus() != null ? s.getStatus() : "N/A"
            });
        }
    }

    private void initEvents() {
        view.getCbClasses().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ClassSectionDTO selected = (ClassSectionDTO) view.getCbClasses().getSelectedItem();
                loadStudentsByClass(selected);
                view.getTxtScore().setText("");
            }
        });

        view.getTableStudents().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = view.getTableStudents().getSelectedRow();
                if (row != -1) {
                    Object scoreObj = view.getTableStudents().getValueAt(row, 3);
                    if (!"Chưa có".equals(scoreObj)) {
                        view.getTxtScore().setText(scoreObj.toString());
                    } else {
                        view.getTxtScore().setText("");
                    }
                }
            }
        });

        view.getBtnSave().addActionListener(e -> handleSaveScore());
    }

    private void handleSaveScore() {
        int row = view.getTableStudents().getSelectedRow();
        if (row == -1) {
            MessageUtil.showError(view, "Vui lòng chọn một sinh viên từ danh sách!");
            return;
        }

        Long enrollmentId = (Long) view.getTableStudents().getValueAt(row, 0);
        String scoreText = view.getTxtScore().getText();
        
        if (scoreText == null || scoreText.trim().isEmpty()) {
            MessageUtil.showError(view, "Vui lòng nhập điểm!");
            return;
        }

        try {
            Double score = Double.parseDouble(scoreText);
            String result = transcriptService.updateEnrollmentScore(enrollmentId, score);
            if (result.equals("SUCCESS")) {
                MessageUtil.showInfo(view, "Cập nhật điểm thành công cho sinh viên!");
                loadStudentsByClass((ClassSectionDTO) view.getCbClasses().getSelectedItem());
            } else {
                MessageUtil.showError(view, result);
            }
        } catch (NumberFormatException ex) {
            MessageUtil.showError(view, "Điểm phải là một số hợp lệ!");
        }
    }
}
