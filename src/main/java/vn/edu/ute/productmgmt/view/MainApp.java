package vn.edu.ute.productmgmt.view;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JTabbedPane tabbedPane;
    private StudentPanel studentPanel;
    private RegistrationPanel registrationPanel;
    private StatisticPanel statisticPanel;

    public MainApp() {
        setTitle("UTE University Management System - Main Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Khởi tạo các SubForm
        studentPanel = new StudentPanel();
        registrationPanel = new RegistrationPanel();
        statisticPanel = new StatisticPanel();

        // Thêm Tab
        tabbedPane.addTab("Quản lý Sinh viên", new ImageIcon(), studentPanel);
        tabbedPane.addTab("Đăng ký Học phần", new ImageIcon(), registrationPanel);
        tabbedPane.addTab("Thống kê & Báo cáo", new ImageIcon(), statisticPanel);

        // Menu Bar đơn giản
        JMenuBar menuBar = new JMenuBar();
        JMenu menuSystem = new JMenu("Hệ thống");
        JMenuItem itemLogout = new JMenuItem("Đăng xuất");
        menuSystem.add(itemLogout);
        menuBar.add(menuSystem);
        setJMenuBar(menuBar);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public StudentPanel getStudentPanel() { return studentPanel; }
    public RegistrationPanel getRegistrationPanel() { return registrationPanel; }
    public StatisticPanel getStatisticPanel() { return statisticPanel; }
}