package vn.edu.ute.productmgmt.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String office;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Major> majors = new ArrayList<>();

    @OneToMany(mappedBy = "faculty")
    private List<Lecturer> lecturers = new ArrayList<>();

    public Faculty() {}

    public Faculty(String name, String office) {
        this.name = name;
        this.office = office;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOffice() { return office; }
    public void setOffice(String office) { this.office = office; }
    public List<Major> getMajors() { return majors; }
    public void setMajors(List<Major> majors) { this.majors = majors; }
    public List<Lecturer> getLecturers() { return lecturers; }
    public void setLecturers(List<Lecturer> lecturers) { this.lecturers = lecturers; }
}