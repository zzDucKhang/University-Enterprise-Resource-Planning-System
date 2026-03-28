package vn.edu.ute.productmgmt.model.dto;

import vn.edu.ute.productmgmt.model.enums.UserRole;

public class AccountDTO {
    private String username;
    private UserRole role;
    private String ownerName;

    public AccountDTO() {}

    public AccountDTO(String username, UserRole role, String ownerName) {
        this.username = username;
        this.role = role;
        this.ownerName = ownerName;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}