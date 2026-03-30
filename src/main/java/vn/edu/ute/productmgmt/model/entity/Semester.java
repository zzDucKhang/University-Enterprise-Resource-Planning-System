package vn.edu.ute.productmgmt.model.entity;

import jakarta.persistence.*;
import vn.edu.ute.productmgmt.model.enums.SemesterStatus;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "semester_name")
    private String semesterName;

    @Enumerated(EnumType.STRING)
    private SemesterStatus status;

    @Column(name = "registration_start_date")
    private java.time.LocalDate registrationStartDate;

    @Column(name = "registration_end_date")
    private java.time.LocalDate registrationEndDate;

    @OneToMany(mappedBy = "semester")
    private List<ClassSection> classSections = new ArrayList<>();

    public Semester() {}

    public Semester(String semesterName, SemesterStatus status) {
        this.semesterName = semesterName;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    public SemesterStatus getStatus() { return status; }
    public void setStatus(SemesterStatus status) { this.status = status; }
    public java.time.LocalDate getRegistrationStartDate() { return registrationStartDate; }
    public void setRegistrationStartDate(java.time.LocalDate registrationStartDate) { this.registrationStartDate = registrationStartDate; }
    public java.time.LocalDate getRegistrationEndDate() { return registrationEndDate; }
    public void setRegistrationEndDate(java.time.LocalDate registrationEndDate) { this.registrationEndDate = registrationEndDate; }
    public List<ClassSection> getClassSections() { return classSections; }
    public void setClassSections(List<ClassSection> classSections) { this.classSections = classSections; }
}