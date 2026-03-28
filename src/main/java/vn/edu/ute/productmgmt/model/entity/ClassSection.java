package vn.edu.ute.productmgmt.model.entity;

import jakarta.persistence.*;
import vn.edu.ute.productmgmt.model.enums.StudyDay;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class_sections")
public class ClassSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classCode;
    private String room;

    @Enumerated(EnumType.STRING)
    private StudyDay dayOfWeek;

    private Integer startPeriod;
    private Integer endPeriod;
    private Integer maxCapacity;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToMany(mappedBy = "classSection")
    private List<Enrollment> enrollments = new ArrayList<>();

    public ClassSection() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public StudyDay getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(StudyDay dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public Integer getStartPeriod() { return startPeriod; }
    public void setStartPeriod(Integer startPeriod) { this.startPeriod = startPeriod; }
    public Integer getEndPeriod() { return endPeriod; }
    public void setEndPeriod(Integer endPeriod) { this.endPeriod = endPeriod; }
    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public Lecturer getLecturer() { return lecturer; }
    public void setLecturer(Lecturer lecturer) { this.lecturer = lecturer; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }
}