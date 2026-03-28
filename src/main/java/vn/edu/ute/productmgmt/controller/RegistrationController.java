package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.dto.AccountDTO;
import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;
import vn.edu.ute.productmgmt.model.entity.Student;
import vn.edu.ute.productmgmt.model.util.MessageUtil;
import vn.edu.ute.productmgmt.model.util.SessionManager;
import vn.edu.ute.productmgmt.service.ClassSectionService;
import vn.edu.ute.productmgmt.service.RegistrationService;
import vn.edu.ute.productmgmt.service.StudentService;
import vn.edu.ute.productmgmt.view.RegistrationPanel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RegistrationController {
    private RegistrationPanel view;
    private RegistrationService registrationService;
    private ClassSectionService classSectionService;
    private StudentService studentService;

    public RegistrationController(RegistrationPanel view) {
        this.view = view;
        this.registrationService = new RegistrationService();
        this.classSectionService = new ClassSectionService();
        this.studentService = new StudentService();

        initEvents();
        loadAvailableClasses();
    }

    private void initEvents() {
        // Nút Đăng ký
        view.getBtnRegister().addActionListener(e -> handleRegistration());

        // Nút Refresh danh sách
        view.getBtnRefresh().addActionListener(e -> loadAvailableClasses());
    }

    /**
     * Tải danh sách các lớp học phần đang mở lên bảng
     */
    private void loadAvailableClasses() {
        List<ClassSectionDTO> list = classSectionService.getAllClassSectionDTOs();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (ClassSectionDTO cs : list) {
            model.addRow(new Object[]{
                    cs.getId(), // Cột ẩn hoặc dùng để lấy ID
                    cs.getClassCode(),
                    cs.getCourseName(),
                    cs.getCredits(),
                    cs.getLecturerName(),
                    cs.getScheduleDisplay(),
                    cs.getCapacityDisplay()
            });
        }
    }

    /**
     * Xử lý logic đăng ký khi nhấn nút
     */
    private void handleRegistration() {
        // 1. Kiểm tra đăng nhập
        if (!SessionManager.isLoggedIn()) {
            MessageUtil.showError(view, "Bạn cần đăng nhập để thực hiện chức năng này!");
            return;
        }

        // 2. Lấy dòng được chọn trên bảng
        int selectedRow = view.getTableClasses().getSelectedRow();
        if (selectedRow == -1) {
            MessageUtil.showError(view, "Vui lòng chọn một lớp học phần từ danh sách!");
            return;
        }

        // 3. Lấy ID lớp học phần (giả sử cột 0 lưu ID)
        Long classSectionId = (Long) view.getTableClasses().getValueAt(selectedRow, 0);

        // 4. Lấy thông tin sinh viên đang đăng nhập từ Session
        AccountDTO currentAcc = SessionManager.getCurrentAccount();
        if (currentAcc == null) return;

        // Tìm Entity Student tương ứng với tài khoản đang login
        // Lưu ý: Tên tài khoản sinh viên thường chính là MSSV
        Student currentStudent = studentService.findByCode(currentAcc.getUsername());

        if (currentStudent == null) {
            MessageUtil.showError(view, "Không tìm thấy hồ sơ sinh viên tương ứng!");
            return;
        }

        // 5. Gọi Service xử lý nghiệp vụ "khó" (Trùng lịch, sĩ số, môn tiên quyết)
        String result = registrationService.registerCourse(currentStudent, classSectionId);

        if (result.equals("SUCCESS")) {
            MessageUtil.showInfo(view, "Chúc mừng! Bạn đã đăng ký học phần thành công.");
            loadAvailableClasses(); // Cập nhật lại sĩ số trên bảng
        } else {
            // Hiển thị lý do thất bại (Trùng lịch, Hết chỗ, Chưa học môn tiên quyết...)
            MessageUtil.showError(view, result);
        }
    }
}