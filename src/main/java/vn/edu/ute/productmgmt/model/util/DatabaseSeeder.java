package vn.edu.ute.productmgmt.model.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.edu.ute.productmgmt.model.entity.*;
import vn.edu.ute.productmgmt.model.enums.*;

import java.util.List;

public class DatabaseSeeder {

    public static void seed() {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            // 1. Seed Khoa & Ngành (Độc lập)
            if (getCount(em, Faculty.class) == 0) {
                seedFacultiesAndMajors(em);
            }

            // 2. Seed Giảng viên (Độc lập)
            if (getCount(em, Lecturer.class) == 0) {
                seedLecturers(em);
            }

            // 3. Seed Môn học
            if (getCount(em, Course.class) == 0) {
                seedCourses(em);
            }

            // 4. Seed Học kỳ
            if (getCount(em, Semester.class) == 0) {
                seedSemesters(em);
            }

            // 5. Seed Sinh viên
            if (getCount(em, Student.class) == 0) {
                seedStudents(em);
            }

            // 6. Seed Lớp học phần
            if (getCount(em, ClassSection.class) == 0) {
                seedClassSections(em);
            }

            // 7. Seed Tài khoản (Kiểm tra từng Role)
            seedAccountsIndependently(em);

            trans.commit();
            System.out.println("------------------------------------------");
            System.out.println(">>> SMART SEEDING COMPLETED SUCCESSFULLY!");
            System.out.println("------------------------------------------");

        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static Long getCount(EntityManager em, Class<?> entityClass) {
        return em.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                .getSingleResult();
    }

    private static void seedFacultiesAndMajors(EntityManager em) {
        String[] fNames = {"Công nghệ Thông tin", "Điện tử", "Cơ khí", "Kinh tế", "Ngoại ngữ"};
        String[] mNames = {"Kỹ thuật Phần mềm", "Hệ thống Thông tin", "Cơ điện tử", "Kinh tế số", "Ngôn ngữ Anh"};
        for (int i = 0; i < 5; i++) {
            Faculty f = new Faculty(); f.setName(fNames[i]); em.persist(f);
            Major m = new Major(); m.setName(mNames[i]); m.setFaculty(f); em.persist(m);
        }
    }

    private static void seedLecturers(EntityManager em) {
        List<Faculty> faculties = em.createQuery("FROM Faculty", Faculty.class).getResultList();
        for (int i = 0; i < 5; i++) {
            Lecturer l = new Lecturer();
            l.setLecturerCode("GV00" + (i + 1));
            l.setFullName("Giảng viên " + (i + 1));
            l.setFaculty(faculties.get(i % faculties.size()));
            l.setGender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE);
            em.persist(l);
        }
    }

    private static void seedCourses(EntityManager em) {
        String[][] cData = {{"IT01", "Java Programming", "3"}, {"IT02", "SQL Database", "4"}};
        for (String[] row : cData) {
            Course c = new Course(); c.setCourseCode(row[0]); c.setTitle(row[1]); c.setCredits(Integer.parseInt(row[2]));
            em.persist(c);
        }
    }

    private static void seedSemesters(EntityManager em) {
        String[] names = {"HK1 2024-2025", "HK2 2024-2025", "HK1 2025-2026"};
        for (String n : names) {
            Semester s = new Semester(); s.setSemesterName(n); em.persist(s);
        }
    }

    private static void seedStudents(EntityManager em) {
        List<Major> majors = em.createQuery("FROM Major", Major.class).getResultList();
        String[] sCodes = {"24110239", "22110123", "22110456", "23110789", "24110001"};
        for (int i = 0; i < sCodes.length; i++) {
            Student s = new Student();
            s.setStudentCode(sCodes[i]);
            s.setFullName("Sinh viên " + sCodes[i]);
            s.setMajor(majors.get(i % majors.size()));
            s.setGender(Gender.MALE);
            s.setEmail(sCodes[i] + "@student.ute.edu.vn");
            em.persist(s);
        }
    }

    private static void seedClassSections(EntityManager em) {
        List<Course> courses = em.createQuery("FROM Course", Course.class).getResultList();
        List<Lecturer> lecturers = em.createQuery("FROM Lecturer", Lecturer.class).getResultList();
        List<Semester> semesters = em.createQuery("FROM Semester", Semester.class).getResultList();
        if (courses.isEmpty() || lecturers.isEmpty() || semesters.isEmpty()) return;

        for (int i = 0; i < courses.size(); i++) {
            ClassSection cs = new ClassSection();
            cs.setClassCode("LHP00" + (i + 1));
            cs.setCourse(courses.get(i));
            cs.setLecturer(lecturers.get(i % lecturers.size()));
            cs.setSemester(semesters.get(semesters.size() - 1));
            cs.setDayOfWeek(StudyDay.MONDAY);
            cs.setStartPeriod(1); cs.setEndPeriod(4);
            cs.setMaxCapacity(50);
            em.persist(cs);
        }
    }

    private static void seedAccountsIndependently(EntityManager em) {
        // Kiểm tra Admin
        long adminCount = em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.role = :r", Long.class)
                .setParameter("r", UserRole.ADMIN).getSingleResult();
        if (adminCount == 0) {
            Account a = new Account(); a.setUsername("admin"); a.setPassword(PasswordUtil.hashPassword("123"));
            a.setRole(UserRole.ADMIN); em.persist(a);
        }

        // Kiểm tra Giảng viên (Dựa trên số lượng account LECTURER)
        long lecAccCount = em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.role = :r", Long.class)
                .setParameter("r", UserRole.LECTURER).getSingleResult();
        if (lecAccCount == 0) {
            List<Lecturer> lecturers = em.createQuery("FROM Lecturer", Lecturer.class).getResultList();
            for (Lecturer l : lecturers) {
                Account a = new Account(); a.setUsername(l.getLecturerCode());
                a.setPassword(PasswordUtil.hashPassword("123"));
                a.setRole(UserRole.LECTURER); a.setLecturer(l); em.persist(a);
            }
        }

        // Kiểm tra Sinh viên (Dựa trên số lượng account STUDENT)
        long stuAccCount = em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.role = :r", Long.class)
                .setParameter("r", UserRole.STUDENT).getSingleResult();
        if (stuAccCount == 0) {
            List<Student> students = em.createQuery("FROM Student", Student.class).getResultList();
            for (Student s : students) {
                Account a = new Account(); a.setUsername(s.getStudentCode());
                a.setPassword(PasswordUtil.hashPassword("123"));
                a.setRole(UserRole.STUDENT); a.setStudent(s); em.persist(a);
            }
        }
    }
}