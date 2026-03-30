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

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "class_code", unique = true, nullable = false)
    private String classCode;

    private String room;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private StudyDay dayOfWeek;

    @Column(name = "start_period")
    private Integer startPeriod;

    @Column(name = "end_period")
    private Integer endPeriod;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "current_enrollment", columnDefinition = "INT DEFAULT 0")
    private Integer currentEnrollment = 0;

    // QUAN HỆ: Nhiều lớp thuộc về một Môn học
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // QUAN HỆ: Nhiều lớp thuộc về một Học kỳ
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    // QUAN HỆ: Nhiều lớp do một Giảng viên dạy
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    // QUAN HỆ: Một lớp có nhiều lượt Đăng ký (Enrollments)
    @OneToMany(mappedBy = "classSection", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    public ClassSection() {}

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

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

    public Integer getCurrentEnrollment() {
        return currentEnrollment;
    }
    public void setCurrentEnrollment(Integer currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }
}