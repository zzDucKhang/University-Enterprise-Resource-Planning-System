package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.LecturerDao;
import vn.edu.ute.productmgmt.model.dto.LecturerDTO;
import vn.edu.ute.productmgmt.model.entity.Lecturer;
import java.util.List;

public class LecturerService {
    private final LecturerDao lecturerDao = new LecturerDao();

    public List<Lecturer> getAll() {
        return lecturerDao.findAll();
    }

    public List<LecturerDTO> getAllDTOs() {
         return lecturerDao.findAllDTO();
    }
}
