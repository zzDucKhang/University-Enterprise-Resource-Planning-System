package vn.edu.ute.productmgmt.model.dto;

import vn.edu.ute.productmgmt.model.enums.Gender;
import java.time.LocalDate;

public class StudentDTO {
    private Long id;
    private String studentCode;
    private String fullName;
    private LocalDate dob;
    private String email;
    private Gender gender;
    private String majorName;
    private String facultyName;
    private Double gpa;
    private Integer totalCredits;

    public StudentDTO() {}

    public StudentDTO(Long id, String studentCode, String fullName, LocalDate dob,
                      String email, Gender gender, String majorName, String facultyName,
                      Double gpa, Long totalCredits) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.majorName = majorName;
        this.facultyName = facultyName;
        this.gpa = (gpa != null) ? gpa : 0.0;
        this.totalCredits = (totalCredits != null) ? totalCredits.intValue() : 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getMajorName() { return majorName; }
    public void setMajorName(String majorName) { this.majorName = majorName; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }

    public Integer getTotalCredits() { return totalCredits; }
    public void setTotalCredits(Integer totalCredits) { this.totalCredits = totalCredits; }

    public String getGpaFormatted() {
        return String.format("%.2f", gpa);
    }
}