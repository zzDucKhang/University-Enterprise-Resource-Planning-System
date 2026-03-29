package vn.edu.ute.productmgmt.model.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", unique = true, nullable = false)
    private String courseCode;

    private String title;
    private Integer credits;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "prerequisites",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    private Set<Course> prerequisites = new HashSet<>();

    public Course() {}

    public Course(String courseCode, String title, Integer credits) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public Set<Course> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(Set<Course> prerequisites) { this.prerequisites = prerequisites; }
}