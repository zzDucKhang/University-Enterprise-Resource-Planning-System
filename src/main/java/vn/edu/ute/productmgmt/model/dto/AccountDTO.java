package vn.edu.ute.productmgmt.model.dto;

import vn.edu.ute.productmgmt.model.enums.UserRole;

public class AccountDTO {
    private Long id;
    private String username;
    private UserRole role;
    private String ownerName;
    private Long studentId;  // ID để dùng trong RegistrationController
    private Long lecturerId; // ID để dùng trong LecturerClassController

    // 1. Constructor mặc định
    public AccountDTO() {}

    // 2. Constructor đầy đủ để khởi tạo nhanh khi Login thành công
    public AccountDTO(Long id, String username, UserRole role, String ownerName, Long studentId, Long lecturerId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.ownerName = ownerName;
        this.studentId = studentId;
        this.lecturerId = lecturerId;
    }

    // --- GETTERS & SETTERS ĐẦY ĐỦ ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }
}