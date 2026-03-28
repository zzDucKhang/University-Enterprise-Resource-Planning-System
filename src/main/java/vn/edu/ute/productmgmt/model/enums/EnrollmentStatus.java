package vn.edu.ute.productmgmt.model.enums;

public enum EnrollmentStatus {
    REGISTERED,   // Vừa đăng ký xong
    IN_PROGRESS,  // Đang đi học
    PASSED,       // Đã hoàn thành và đạt (Điểm >= 5.0)
    FAILED        // Không đạt (Phải học lại)
}