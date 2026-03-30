package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.dto.AccountDTO;
import vn.edu.ute.productmgmt.model.dto.EnrollmentDTO;
import vn.edu.ute.productmgmt.model.util.SessionManager;
import vn.edu.ute.productmgmt.service.TranscriptService;
import vn.edu.ute.productmgmt.view.StudentTranscriptPanel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentTranscriptController {
    private StudentTranscriptPanel view;
    private TranscriptService transcriptService;

    public StudentTranscriptController(StudentTranscriptPanel view) {
        this.view = view;
        this.transcriptService = new TranscriptService();
        initEvents();
        loadStudentTranscript();
    }

    private void initEvents() {
        view.getBtnRefresh().addActionListener(e -> loadStudentTranscript());
    }

    /**
     * Tự động quét ID Sinh viên đang truy cập hệ thống và đổ bảng điểm lên View.
     */
    public void loadStudentTranscript() {
        AccountDTO currentAcc = SessionManager.getCurrentAccount();
        if (currentAcc == null || currentAcc.getStudentId() == null) {
            return;
        }

        List<EnrollmentDTO> transcript = transcriptService.getStudentTranscript(currentAcc.getStudentId());
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        int totalCredits = 0;
        double totalPoints10 = 0.0;

        for (EnrollmentDTO dto : transcript) {
            // Hiển thị Điểm là chuỗi "N/A" (Chưa có) nếu Giáo viên chưa chấm
            String scoreDisplay = (dto.getScore() != null) ? String.format("%.1f", dto.getScore()) : "Chưa có";
            
            // Dịch thuật trạng thái (Enum)
            String statusDisplay = "Chưa học";
            if (dto.getStatus() != null) {
                switch (dto.getStatus()) {
                    case PASSED: statusDisplay = "ĐẠT"; break;
                    case FAILED: statusDisplay = "RỚT"; break;
                    case REGISTERED: statusDisplay = "ĐANG HỌC"; break;
                }
            }

            model.addRow(new Object[]{
                    dto.getCourseCode(),
                    dto.getCourseName(),
                    dto.getCredits(),
                    scoreDisplay,
                    statusDisplay
            });

            // Tính tổng Tín chỉ và điểm trung bình hệ 10 cho những môn "ĐÃ CÓ ĐIỂM"
            if (dto.getScore() != null) {
                totalCredits += dto.getCredits();
                totalPoints10 += (dto.getScore() * dto.getCredits());
            }
        }

        // --- TÍNH TOÁN THỐNG KÊ (GPA DƯỚI CÙNG) ---
        view.getLblTotalCredits().setText("Tổng số tín chỉ hoàn thành: " + totalCredits);

        if (totalCredits > 0) {
            double gpa10 = totalPoints10 / totalCredits;
            double gpa4 = transcriptService.calculateSemesterGPA(transcript);

            view.getLblGpa10().setText(String.format("Điểm TB Hệ 10: %.2f", gpa10));
            view.getLblGpa4().setText(String.format("Điểm TB Hệ 4: %.2f", gpa4));
        } else {
            view.getLblGpa10().setText("Điểm TB Hệ 10: 0.00");
            view.getLblGpa4().setText("Điểm TB Hệ 4: 0.00");
        }
        
        System.out.println(">>> Đã làm mới Bảng Điểm Cá Nhân.");
    }
}
