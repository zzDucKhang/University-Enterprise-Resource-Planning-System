-- =============================================================================
-- Dữ liệu mẫu (tương đương logic DatabaseSeeder.java)
-- Mật khẩu tài khoản: 123 (SHA-256 + Base64 — khớp PasswordUtil.hashPassword)
-- Hash: pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=
--
-- Chạy SAU schema.sql, cùng database:
--   mysql -u USER -p DATABASE < sql/data.sql
-- =============================================================================

SET NAMES utf8mb4;
USE university_erp_db;

-- Dọn dữ liệu cũ để script có thể chạy nhiều lần mà không lỗi trùng khóa
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE accounts;
TRUNCATE TABLE enrollments;
TRUNCATE TABLE class_sections;
TRUNCATE TABLE prerequisites;
TRUNCATE TABLE students;
TRUNCATE TABLE lecturers;
TRUNCATE TABLE semesters;
TRUNCATE TABLE courses;
TRUNCATE TABLE majors;
TRUNCATE TABLE faculties;
SET FOREIGN_KEY_CHECKS = 1;

SET @pwd_123 := 'pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=';

-- Khoa
INSERT INTO faculties (id, name, office) VALUES
                                             (1, 'Công nghệ Thông tin', 'Tòa A1'),
                                             (2, 'Điện tử', 'Tòa A2'),
                                             (3, 'Cơ khí', 'Tòa B1'),
                                             (4, 'Kinh tế', 'Tòa C1'),
                                             (5, 'Ngoại ngữ', 'Tòa D1');

-- Ngành
INSERT INTO majors (id, name, faculty_id) VALUES
                                              (1, 'Kỹ thuật Phần mềm', 1),
                                              (2, 'Hệ thống Thông tin', 1),
                                              (3, 'Cơ điện tử', 2),
                                              (4, 'Kinh tế số', 4),
                                              (5, 'Ngôn ngữ Anh', 5);

-- Môn học
INSERT INTO courses (id, course_code, title, credits) VALUES
                                                          (1, 'IT01', 'Java Programming', 3),
                                                          (2, 'IT02', 'SQL Database', 4);

-- Tiên quyết: IT02 cần IT01 (minh họa quan hệ prerequisites)
INSERT INTO prerequisites (course_id, prerequisite_id) VALUES
    (2, 1);

-- Học kỳ (Ngày bắt đầu và kết thúc đăng ký lấy tự động tính từ hôm nay cho dễ test)
INSERT INTO semesters (id, semester_name, status, registration_start_date, registration_end_date) VALUES
    (1, 'HK1 2024-2025', 'OPENING', DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_ADD(CURDATE(), INTERVAL 20 DAY)),
    (2, 'HK2 2024-2025', 'ONGOING', DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_ADD(CURDATE(), INTERVAL 20 DAY)),
    (3, 'HK1 2025-2026', 'OPENING', DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_ADD(CURDATE(), INTERVAL 20 DAY));

-- Giảng viên
INSERT INTO lecturers (id, lecturer_code, full_name, degree, gender, faculty_id) VALUES
                                                                                     (1, 'GV001', 'Giảng viên 1', 'ThS', 'MALE', 1),
                                                                                     (2, 'GV002', 'Giảng viên 2', 'ThS', 'FEMALE', 2),
                                                                                     (3, 'GV003', 'Giảng viên 3', 'TS', 'MALE', 3),
                                                                                     (4, 'GV004', 'Giảng viên 4', 'ThS', 'FEMALE', 4),
                                                                                     (5, 'GV005', 'Giảng viên 5', 'TS', 'MALE', 5);

-- Sinh viên
INSERT INTO students (id, student_code, full_name, dob, email, gender, major_id) VALUES
                                                                                     (1, '24110239', 'Sinh viên 24110239', '2006-01-15', '24110239@student.ute.edu.vn', 'MALE', 1),
                                                                                     (2, '22110123', 'Sinh viên 22110123', '2004-05-20', '22110123@student.ute.edu.vn', 'MALE', 2),
                                                                                     (3, '22110456', 'Sinh viên 22110456', '2004-08-10', '22110456@student.ute.edu.vn', 'FEMALE', 3),
                                                                                     (4, '23110789', 'Sinh viên 23110789', '2005-03-01', '23110789@student.ute.edu.vn', 'MALE', 4),
                                                                                     (5, '24110001', 'Sinh viên 24110001', '2006-11-22', '24110001@student.ute.edu.vn', 'FEMALE', 5);

-- Lớp học phần (Thêm cột version để khớp với Optimistic Locking Entity)
INSERT INTO class_sections (id, class_code, room, day_of_week, start_period, end_period, max_capacity, course_id, semester_id, lecturer_id, version) VALUES
    (1, 'LHP001', 'P.301', 'MONDAY', 1, 4, 50, 1, 3, 1, 0),
    (2, 'LHP002', 'P.302', 'MONDAY', 1, 4, 50, 2, 3, 2, 0);

-- Ghi danh mẫu (Giả lập giống DatabaseSeeder để test điểm hệ 4.0 và Rank)
INSERT INTO enrollments (id, score, status, student_id, class_section_id) VALUES
    (1, 9.5, 'PASSED', 1, 1),
    (2, 7.0, 'PASSED', 1, 2),
    (3, 4.5, 'FAILED', 2, 1),
    (4, 8.5, 'PASSED', 2, 2),
    (5, 6.0, 'PASSED', 3, 1),
    (6, 10.0, 'PASSED', 3, 2),
    (7, NULL, 'REGISTERED', 4, 1), -- Trường hợp chờ Giảng viên chấm điểm
    (8, NULL, 'REGISTERED', 4, 2); -- Trường hợp chờ Giảng viên chấm điểm

-- Tài khoản: admin (không gắn SV/GV)
INSERT INTO accounts (id, username, password, role, student_id, lecturer_id) VALUES
    (1, 'admin', @pwd_123, 'ADMIN', NULL, NULL);

-- Tài khoản giảng viên (username = mã GV)
INSERT INTO accounts (id, username, password, role, student_id, lecturer_id) VALUES
                                                                                 (2, 'GV001', @pwd_123, 'LECTURER', NULL, 1),
                                                                                 (3, 'GV002', @pwd_123, 'LECTURER', NULL, 2),
                                                                                 (4, 'GV003', @pwd_123, 'LECTURER', NULL, 3),
                                                                                 (5, 'GV004', @pwd_123, 'LECTURER', NULL, 4),
                                                                                 (6, 'GV005', @pwd_123, 'LECTURER', NULL, 5);

-- Tài khoản sinh viên (username = MSSV)
INSERT INTO accounts (id, username, password, role, student_id, lecturer_id) VALUES
                                                                                 (7, '24110239', @pwd_123, 'STUDENT', 1, NULL),
                                                                                 (8, '22110123', @pwd_123, 'STUDENT', 2, NULL),
                                                                                 (9, '22110456', @pwd_123, 'STUDENT', 3, NULL),
                                                                                 (10, '23110789', @pwd_123, 'STUDENT', 4, NULL),
                                                                                 (11, '24110001', @pwd_123, 'STUDENT', 5, NULL);

-- Đồng bộ AUTO_INCREMENT (tránh trùng ID khi Hibernate insert thêm)
ALTER TABLE faculties AUTO_INCREMENT = 6;
ALTER TABLE majors AUTO_INCREMENT = 6;
ALTER TABLE courses AUTO_INCREMENT = 3;
ALTER TABLE semesters AUTO_INCREMENT = 4;
ALTER TABLE lecturers AUTO_INCREMENT = 6;
ALTER TABLE students AUTO_INCREMENT = 6;
ALTER TABLE class_sections AUTO_INCREMENT = 3;
ALTER TABLE enrollments AUTO_INCREMENT = 9;
ALTER TABLE accounts AUTO_INCREMENT = 12;