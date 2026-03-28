package vn.edu.ute.productmgmt.model.dto;

public class CourseDTO {
    private Long id;
    private String courseCode;
    private String title;
    private Integer credits;
    private String prerequisiteSummary;

    public CourseDTO() {}

    public CourseDTO(Long id, String courseCode, String title, Integer credits, String prerequisiteSummary) {
        this.id = id;
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.prerequisiteSummary = prerequisiteSummary;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getPrerequisiteSummary() { return prerequisiteSummary; }
    public void setPrerequisiteSummary(String prerequisiteSummary) { this.prerequisiteSummary = prerequisiteSummary; }
}