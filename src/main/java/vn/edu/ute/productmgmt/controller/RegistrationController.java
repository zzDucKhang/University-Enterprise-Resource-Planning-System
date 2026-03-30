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
        loadAvailableClasses(); // Nạp dữ liệu ngay khi khởi tạo
    }

    /**
     * Tải danh sách các lớp học phần đang mở lên bảng.
     * Hàm này để PUBLIC để MainController có thể ra lệnh làm mới khi chuyển Tab.
     */
    public void loadAvailableClasses() {
        List<ClassSectionDTO> list = classSectionService.getAllClassSectionDTOs();
        DefaultTableModel model = view.getTableModel();

        // Xóa sạch dữ liệu cũ trước khi nạp mới
        model.setRowCount(0);

        for (ClassSectionDTO cs : list) {
            model.addRow(new Object[]{
                    cs.getId(),             // Cột 0: ID (Thường dùng để ẩn hoặc lấy ID xử lý)
                    cs.getClassCode(),       // Cột 1: Mã lớp
                    cs.getCourseName(),      // Cột 2: Tên môn học
                    cs.getCredits(),         // Cột 3: Số tín chỉ
                    cs.getLecturerDisplayName(), // Cột 4: Giảng viên
                    cs.getScheduleDisplay(), // Cột 5: Lịch học (Thứ, Tiết)
                    cs.getCapacityDisplay()  // Cột 6: Sĩ số (Ví dụ: 45/50)
            });
        }
        System.out.println(">>> Đã cập nhật danh sách đăng ký học phần.");
    }

    private void initEvents() {
        // Gán sự kiện cho nút Đăng ký
        view.getBtnRegister().addActionListener(e -> handleRegistration());

        // Gán sự kiện cho nút Làm mới (Refresh)
        view.getBtnRefresh().addActionListener(e -> loadAvailableClasses());
    }

    /**
     * Xử lý logic đăng ký khi người dùng nhấn nút
     */
    private void handleRegistration() {
        // 1. Kiểm tra trạng thái đăng nhập
        if (!SessionManager.isLoggedIn()) {
            MessageUtil.showError(view, "Bạn cần đăng nhập để thực hiện chức năng này!");
            return;
        }

        // 2. Lấy dòng được chọn trên bảng lớp học
        int selectedRow = view.getTableClasses().getSelectedRow();
        if (selectedRow == -1) {
            MessageUtil.showError(view, "Vui lòng chọn một lớp học phần từ danh sách!");
            return;
        }

        // 3. Lấy ID lớp học phần từ cột đầu tiên (Cột 0)
        Long classSectionId = (Long) view.getTableClasses().getValueAt(selectedRow, 0);

        // 4. Lấy thông tin tài khoản đang login từ SessionManager
        AccountDTO currentAcc = SessionManager.getCurrentAccount();
        if (currentAcc == null) return;

        // Tìm đối tượng Student tương ứng (Giả định Username chính là MSSV)
        Student currentStudent = studentService.findByCode(currentAcc.getUsername());

        if (currentStudent == null) {
            MessageUtil.showError(view, "Không tìm thấy hồ sơ sinh viên tương ứng với tài khoản này!");
            return;
        }

        // 5. Gọi Service xử lý nghiệp vụ đăng ký (Check trùng lịch, sĩ số, môn tiên quyết...)
        String result = registrationService.registerCourse(currentStudent, classSectionId);

        if (result.equals("SUCCESS")) {
            MessageUtil.showInfo(view, "Chúc mừng! Bạn đã đăng ký học phần thành công.");
            loadAvailableClasses(); // Nạp lại bảng để cập nhật sĩ số mới nhất
        } else {
            // Hiển thị lý do đăng ký thất bại từ Service trả về
            MessageUtil.showError(view, result);
        }
    }
}