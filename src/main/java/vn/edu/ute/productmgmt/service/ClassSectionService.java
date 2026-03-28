package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.ClassSectionDao;
import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;
import vn.edu.ute.productmgmt.model.entity.ClassSection;
import java.util.List;

public class ClassSectionService {
    private final ClassSectionDao classSectionDao = new ClassSectionDao();

    /**
     * Lấy toàn bộ danh sách lớp học phần dưới dạng DTO để hiển thị lên JTable
     */
    public List<ClassSectionDTO> getAllClassSectionDTOs() {
        return classSectionDao.findAllDTO();
    }

    /**
     * Lấy danh sách lớp theo từng học kỳ (Phục vụ màn hình Đăng ký học phần)
     */
    public List<ClassSectionDTO> getDTOsBySemester(Long semesterId) {
        if (semesterId == null) return getAllClassSectionDTOs();
        return classSectionDao.findDTOBySemester(semesterId);
    }

    /**
     * Lưu hoặc cập nhật một lớp học phần
     */
    public String saveClassSection(ClassSection section) {
        // Validation cơ bản
        if (section.getMaxCapacity() <= 0) {
            return "Sĩ số tối đa phải lớn hơn 0!";
        }
        if (section.getStartPeriod() >= section.getEndPeriod()) {
            return "Tiết bắt đầu phải nhỏ hơn tiết kết thúc!";
        }

        try {
            classSectionDao.save(section);
            return "SUCCESS";
        } catch (Exception e) {
            return "Lỗi khi lưu lớp học phần: " + e.getMessage();
        }
    }

    /**
     * Xóa lớp học phần theo ID
     */
    public void deleteClassSection(Long id) {
        classSectionDao.deleteById(id);
    }

    /**
     * Đếm tổng số lớp học phần đang có (Dùng cho StatisticController)
     */
    public long countTotal() {
        // Tận dụng hàm findAll().size() hoặc viết một câu COUNT trong DAO
        return classSectionDao.findAll().size();
    }
}