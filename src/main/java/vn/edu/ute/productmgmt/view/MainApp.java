package vn.edu.ute.productmgmt.view;

import vn.edu.ute.productmgmt.model.enums.UserRole;
import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JTabbedPane tabbedPane;
    private StudentPanel studentPanel;
    private RegistrationPanel registrationPanel;
    private StatisticPanel statisticPanel;
    private LecturerClassPanel lecturerClassPanel;
    private JMenuItem itemLogout;

    public MainApp(UserRole role) {
        setTitle("UTE University Management System - [" + role + "]");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents(role);
    }

    private void initComponents(UserRole role) {
        tabbedPane = new JTabbedPane();

        // PHÂN QUYỀN KHỞI TẠO TAB
        switch (role) {
            case ADMIN:
                studentPanel = new StudentPanel();
                registrationPanel = new RegistrationPanel();
                statisticPanel = new StatisticPanel();
                lecturerClassPanel = new LecturerClassPanel();

                tabbedPane.addTab("Quản lý Sinh viên", studentPanel);
                tabbedPane.addTab("Quản lý Lớp học", lecturerClassPanel);
                tabbedPane.addTab("Đăng ký Học phần", registrationPanel);
                tabbedPane.addTab("Thống kê & Báo cáo", statisticPanel);
                break;

            case LECTURER:
                lecturerClassPanel = new LecturerClassPanel();
                statisticPanel = new StatisticPanel();

                tabbedPane.addTab("Lịch dạy của tôi", lecturerClassPanel);
                tabbedPane.addTab("Thống kê đào tạo", statisticPanel);
                break;

            case STUDENT:
                registrationPanel = new RegistrationPanel();
                statisticPanel = new StatisticPanel(); // SV có thể xem thống kê cá nhân

                tabbedPane.addTab("Đăng ký Học phần", registrationPanel);
                tabbedPane.addTab("Kết quả học tập", statisticPanel);
                break;
        }

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuSystem = new JMenu("Hệ thống");
        itemLogout = new JMenuItem("Đăng xuất");
        menuSystem.add(itemLogout);
        menuBar.add(menuSystem);
        setJMenuBar(menuBar);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public StudentPanel getStudentPanel() { return studentPanel; }
    public RegistrationPanel getRegistrationPanel() { return registrationPanel; }
    public StatisticPanel getStatisticPanel() { return statisticPanel; }
    public LecturerClassPanel getLecturerClassPanel() { return lecturerClassPanel; }
    public JMenuItem getItemLogout() { return itemLogout; }
    public JTabbedPane getTabbedPane() { return tabbedPane; }
}