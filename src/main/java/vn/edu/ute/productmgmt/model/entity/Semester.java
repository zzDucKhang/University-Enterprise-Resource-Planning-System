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
    public List<ClassSection> getClassSections() { return classSections; }
    public void setClassSections(List<ClassSection> classSections) { this.classSections = classSections; }
}