package vn.edu.ute.productmgmt.model.entity;

import jakarta.persistence.*;
import vn.edu.ute.productmgmt.model.enums.Gender;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecturers")
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecturer_code", unique = true, nullable = false)
    private String lecturerCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "degree")
    private String degree;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "lecturer")
    private List<ClassSection> classSections = new ArrayList<>();

    public Lecturer() {}

    public Lecturer(String lecturerCode, String fullName, String degree, Gender gender, Faculty faculty) {
        this.lecturerCode = lecturerCode;
        this.fullName = fullName;
        this.degree = degree;
        this.gender = gender;
        this.faculty = faculty;
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
    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }
    public List<ClassSection> getClassSections() { return classSections; }
    public void setClassSections(List<ClassSection> classSections) { this.classSections = classSections; }
}