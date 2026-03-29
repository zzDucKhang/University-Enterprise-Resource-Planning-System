package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.MajorDao;
import vn.edu.ute.productmgmt.model.entity.Major;
import java.util.List;

public class MajorService {
    private final MajorDao majorDao = new MajorDao();

    public List<Major> getAllMajors() {
        return majorDao.findAll(); // Chỉ cần hàm này là đủ
    }
}