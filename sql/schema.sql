CREATE DATABASE IF NOT EXISTS university_erp_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE university_erp_db;

-- 1. Bảng Khoa (khớp entity Faculty: name unique, office)
CREATE TABLE faculties (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           office VARCHAR(255) NULL,
                           UNIQUE KEY uk_faculties_name (name)
) ENGINE=InnoDB;

-- 2. Bảng Ngành
CREATE TABLE majors (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        faculty_id BIGINT,
                        FOREIGN KEY (faculty_id) REFERENCES faculties(id)
) ENGINE=InnoDB;

-- 3. Bảng Sinh viên
CREATE TABLE students (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          student_code VARCHAR(20) UNIQUE NOT NULL,
                          full_name VARCHAR(255) NOT NULL,
                          dob DATE,
                          email VARCHAR(255),
                          gender VARCHAR(10), -- MALE, FEMALE, OTHER
                          major_id BIGINT,
                          FOREIGN KEY (major_id) REFERENCES majors(id)
) ENGINE=InnoDB;

-- 4. Bảng Giảng viên
CREATE TABLE lecturers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           lecturer_code VARCHAR(20) UNIQUE NOT NULL,
                           full_name VARCHAR(255) NOT NULL,
                           degree VARCHAR(50),
                           gender VARCHAR(10),
                           faculty_id BIGINT,
                           FOREIGN KEY (faculty_id) REFERENCES faculties(id)
) ENGINE=InnoDB;

-- 5. Bảng Môn học
CREATE TABLE courses (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         course_code VARCHAR(20) UNIQUE NOT NULL,
                         title VARCHAR(255) NOT NULL,
                         credits INT NOT NULL
) ENGINE=InnoDB;

-- 6. Bảng môn tiên quyết (N-N; tên bảng = prerequisites — khớp @JoinTable trong Course)
CREATE TABLE prerequisites (
                               course_id BIGINT NOT NULL,
                               prerequisite_id BIGINT NOT NULL,
                               PRIMARY KEY (course_id, prerequisite_id),
                               FOREIGN KEY (course_id) REFERENCES courses(id),
                               FOREIGN KEY (prerequisite_id) REFERENCES courses(id)
) ENGINE=InnoDB;

-- 7. Bảng Học kỳ
CREATE TABLE semesters (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           semester_name VARCHAR(50) NOT NULL,
                           registration_start_date DATE, -- Định tuyến thời gian đăng ký
                           registration_end_date DATE,
                           status VARCHAR(20) -- PREPARING, OPENING, ONGOING, CLOSED (SemesterStatus)
) ENGINE=InnoDB;

-- 8. Bảng Lớp học phần
CREATE TABLE class_sections (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                version BIGINT DEFAULT 0, -- Chống cấp phát lố sĩ số (Optimistic Locking)
                                class_code VARCHAR(20) UNIQUE NOT NULL,
                                course_id BIGINT,
                                lecturer_id BIGINT,
                                semester_id BIGINT,
                                day_of_week VARCHAR(15), -- MONDAY, TUESDAY...
                                start_period INT,
                                end_period INT,
                                room VARCHAR(50),
                                max_capacity INT DEFAULT 50,
                                FOREIGN KEY (course_id) REFERENCES courses(id),
                                FOREIGN KEY (lecturer_id) REFERENCES lecturers(id),
                                FOREIGN KEY (semester_id) REFERENCES semesters(id)
) ENGINE=InnoDB;

-- 9. Bảng Đăng ký & Điểm số
CREATE TABLE enrollments (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             student_id BIGINT,
                             class_section_id BIGINT,
                             score DOUBLE DEFAULT NULL,
                             status VARCHAR(20), -- REGISTERED, IN_PROGRESS, PASSED, FAILED
                             FOREIGN KEY (student_id) REFERENCES students(id),
                             FOREIGN KEY (class_section_id) REFERENCES class_sections(id)
) ENGINE=InnoDB;

-- 10. Bảng Tài khoản (Sử dụng Password Hash SHA-256)
CREATE TABLE accounts (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(50) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(20), -- ADMIN, LECTURER, STUDENT
                          student_id BIGINT UNIQUE NULL,
                          lecturer_id BIGINT UNIQUE NULL,
                          FOREIGN KEY (student_id) REFERENCES students(id),
                          FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)
) ENGINE=InnoDB;