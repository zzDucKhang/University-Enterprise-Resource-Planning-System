package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.ClassSectionDao;
import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;
import vn.edu.ute.productmgmt.model.entity.ClassSection;
import java.util.List;

// Lớp học
public class ClassSectionService {
    private final ClassSectionDao classSectionDao = new ClassSectionDao();

    // Hàm lấy tất cả lớp học
    public List<ClassSectionDTO> getAllClassSectionDTOs() {
        return classSectionDao.findAllDTO();
    }

    public List<ClassSectionDTO> getDTOsBySemester(Long semesterId) {
        if (semesterId == null) return getAllClassSectionDTOs();
        return classSectionDao.findDTOBySemester(semesterId);
    }

    public ClassSection findById(Long id) {
        return classSectionDao.findById(id);
    }

   // Hàm lấy các lịch dạy của giảng viên
    public List<ClassSectionDTO> getClassesByLecturer(Long lecturerId) {
        if (lecturerId == null) return java.util.Collections.emptyList();
        return classSectionDao.findByLecturerId(lecturerId);
    }

    public String saveClassSection(ClassSection section) {
        if (section.getMaxCapacity() == null || section.getMaxCapacity() <= 0) {
            return "Sĩ số tối đa phải lớn hơn 0!";
        }
        if (section.getStartPeriod() == null || section.getEndPeriod() == null ||
                section.getStartPeriod() >= section.getEndPeriod()) {
            return "Tiết học không hợp lệ (Tiết bắt đầu < Tiết kết thúc)!";
        }

        try {
            classSectionDao.save(section);
            return "SUCCESS";
        } catch (Exception e) {
            return "Lỗi khi lưu lớp: " + e.getMessage();
        }
    }

    public void deleteClassSection(Long id) {
        classSectionDao.deleteById(id);
    }

    public long countTotal() {
        return classSectionDao.countTotalClasses();
    }
}