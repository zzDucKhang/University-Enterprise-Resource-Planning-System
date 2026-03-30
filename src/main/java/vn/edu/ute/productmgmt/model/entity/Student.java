package vn.edu.ute.productmgmt.model.entity;

import jakarta.persistence.*;
import vn.edu.ute.productmgmt.model.enums.Gender;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", unique = true, nullable = false)
    private String studentCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private LocalDate dob;
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private Account account;

    public Account getAccount() { return account; }
    public void setAccount(Account account) {
        this.account = account;
        // Thiết lập mối quan hệ 2 chiều
        if (account != null) {
            account.setStudent(this);
        }
    }

    public Student() {}

    public Student(String studentCode, String fullName, LocalDate dob, String email, Gender gender, Major major) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.major = major;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }
}