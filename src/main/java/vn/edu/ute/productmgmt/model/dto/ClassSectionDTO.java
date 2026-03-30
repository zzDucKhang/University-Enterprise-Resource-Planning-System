package vn.edu.ute.productmgmt.model.dto;

import vn.edu.ute.productmgmt.model.enums.StudyDay;

public class ClassSectionDTO {
    private Long id;
    private String classCode;
    private String courseName;
    private Integer credits;
    private String lecturerName;
    private String lecturerCode;
    private String semesterName;
    private StudyDay dayOfWeek;
    private Integer startPeriod;
    private Integer endPeriod;
    private String room;
    private Long currentEnrollment;
    private Integer maxCapacity;

    public ClassSectionDTO() {}

    public ClassSectionDTO(Long id, String classCode, String courseName, Integer credits,
                           String lecturerName, String lecturerCode, String semesterName, StudyDay dayOfWeek,
                           Integer startPeriod, Integer endPeriod, String room,
                           Long currentEnrollment, Integer maxCapacity) {
        this.id = id;
        this.classCode = classCode;
        this.courseName = courseName;
        this.credits = credits;
        this.lecturerName = lecturerName;
        this.lecturerCode = lecturerCode;
        this.semesterName = semesterName;
        this.dayOfWeek = dayOfWeek;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.room = room;
        this.currentEnrollment = (currentEnrollment != null) ? currentEnrollment : 0L;
        this.maxCapacity = maxCapacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getLecturerName() { return lecturerName; }
    public void setLecturerName(String lecturerName) { this.lecturerName = lecturerName; }

    public String getLecturerCode() { return lecturerCode; }
    public void setLecturerCode(String lecturerCode) { this.lecturerCode = lecturerCode; }

    public String getLecturerDisplayName() {
        if (lecturerName != null) {
            String t = lecturerName.trim();
            if (!t.isEmpty()) {
                return t;
            }
        }
        if (lecturerCode != null) {
            String c = lecturerCode.trim();
            if (!c.isEmpty()) {
                return c;
            }
        }
        return "Chưa phân công";
    }

    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }

    public StudyDay getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(StudyDay dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Integer getStartPeriod() { return startPeriod; }
    public void setStartPeriod(Integer startPeriod) { this.startPeriod = startPeriod; }

    public Integer getEndPeriod() { return endPeriod; }
    public void setEndPeriod(Integer endPeriod) { this.endPeriod = endPeriod; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public Long getCurrentEnrollment() { return currentEnrollment; }
    public void setCurrentEnrollment(Long currentEnrollment) { this.currentEnrollment = currentEnrollment; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public String getScheduleDisplay() {
        return dayOfWeek + " (" + startPeriod + "-" + endPeriod + ")";
    }

    public String getCapacityDisplay() {
        return currentEnrollment + "/" + maxCapacity;
    }

    public boolean isFull() {
        return currentEnrollment >= maxCapacity;
    }
}
