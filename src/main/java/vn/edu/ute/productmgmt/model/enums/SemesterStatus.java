package vn.edu.ute.productmgmt.model.enums;

public enum SemesterStatus {
    PREPARING,  // Đang chuẩn bị (chưa hiện cho SV)
    OPENING,    // Đang mở đăng ký (SV được phép đăng ký)
    ONGOING,    // Đang trong quá trình học (đóng đăng ký)
    CLOSED      // Đã kết thúc học kỳ
}