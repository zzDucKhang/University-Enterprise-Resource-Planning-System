package vn.edu.ute.productmgmt.model.dto;

import vn.edu.ute.productmgmt.model.enums.Gender;

public class LecturerDTO {
    private Long id;
    private String lecturerCode;
    private String fullName;
    private String degree;
    private Gender gender;
    private String facultyName;

    public LecturerDTO() {}

    public LecturerDTO(Long id, String lecturerCode, String fullName, String degree, Gender gender, String facultyName) {
        this.id = id;
        this.lecturerCode = lecturerCode;
        this.fullName = fullName;
        this.degree = degree;
        this.gender = gender;
        this.facultyName = facultyName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLecturerCode() { return lecturerCode; }
    public void setLecturerCode(String lecturerCode) { this.lecturerCode = lecturerCode; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }
}