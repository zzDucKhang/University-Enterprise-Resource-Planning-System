package vn.edu.ute.productmgmt.model.dto;

import vn.edu.ute.productmgmt.model.enums.EnrollmentStatus;

public class EnrollmentDTO {
    private String courseCode;
    private String courseName;
    private Integer credits;
    private Double score;
    private EnrollmentStatus status;

    // Fields dùng cho màn hình Chấm Điểm
    private Long id;
    private String studentCode;
    private String studentName;

    public EnrollmentDTO() {}

    public EnrollmentDTO(String courseCode, String courseName, Integer credits, Double score, EnrollmentStatus status) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.score = score;
        this.status = status;
    }

    public EnrollmentDTO(Long id, String studentCode, String studentName, Double score, EnrollmentStatus status) {
        this.id = id;
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.score = score;
        this.status = status;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
}