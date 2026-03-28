package vn.edu.ute.productmgmt.model.dto;

import vn.edu.ute.productmgmt.model.enums.SemesterStatus;

public class SemesterDTO {
    private Long id;
    private String semesterName;
    private SemesterStatus status;

    public SemesterDTO() {}

    public SemesterDTO(Long id, String semesterName, SemesterStatus status) {
        this.id = id;
        this.semesterName = semesterName;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }

    public SemesterStatus getStatus() { return status; }
    public void setStatus(SemesterStatus status) { this.status = status; }
}