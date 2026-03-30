package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.util.SessionManager;
import vn.edu.ute.productmgmt.view.LoginView;
import vn.edu.ute.productmgmt.view.MainApp;
import javax.swing.*;

public class MainController {
    private MainApp mainApp;
    private StudentController studentController;
    private RegistrationController registrationController;
    private StatisticController statisticController;
    private LecturerClassController lecturerClassController;
    private ClassAdminController classAdminController;
    private GradeController gradeController;
    private StudentTranscriptController studentTranscriptController;

    public MainController(MainApp mainApp) {
        this.mainApp = mainApp;

        // Khởi tạo các Controller con CHỈ KHI Panel tồn tại (đã vượt qua phân quyền)
        if (mainApp.getStudentPanel() != null) {
            this.studentController = new StudentController(mainApp.getStudentPanel(), this);
        }
        if (mainApp.getRegistrationPanel() != null) {
            this.registrationController = new RegistrationController(mainApp.getRegistrationPanel());
        }
        if (mainApp.getStatisticPanel() != null) {
            this.statisticController = new StatisticController(mainApp.getStatisticPanel());
        }
        if (mainApp.getLecturerClassPanel() != null) {
            this.lecturerClassController = new LecturerClassController(mainApp.getLecturerClassPanel());
        }
        if (mainApp.getClassAdminPanel() != null) {
            this.classAdminController = new ClassAdminController(mainApp.getClassAdminPanel());
        }
        if (mainApp.getGradePanel() != null) {
            this.gradeController = new GradeController(mainApp.getGradePanel());
        }
        if (mainApp.getStudentTranscriptPanel() != null) {
            this.studentTranscriptController = new StudentTranscriptController(mainApp.getStudentTranscriptPanel());
        }

        initMenuEvents();
        initTabEvents();
    }

    public void refreshAllData() {
        if (studentController != null) studentController.loadData();
        if (registrationController != null) registrationController.loadAvailableClasses();
        if (statisticController != null) statisticController.refreshStats();
        if (lecturerClassController != null) lecturerClassController.loadLecturerClasses();
        if (classAdminController != null) classAdminController.loadData();
        if (gradeController != null) gradeController.loadClasses();
        if (studentTranscriptController != null) studentTranscriptController.loadStudentTranscript();
        System.out.println(">>> Dữ liệu toàn hệ thống đã được đồng bộ.");
    }

    private void initTabEvents() {
        mainApp.getTabbedPane().addChangeListener(e -> refreshAllData());
    }

    private void initMenuEvents() {
        mainApp.getItemLogout().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainApp,
                    "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                SessionManager.logout();
                mainApp.dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginView loginView = new LoginView();
                    new LoginController(loginView);
                    loginView.setVisible(true);
                });
            }
        });
    }
}