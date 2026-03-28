package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.FacultyDao;
import vn.edu.ute.productmgmt.model.entity.Faculty;

import java.util.List;

public class FacultyService {
    private final FacultyDao facultyDao = new FacultyDao();
    public List<Faculty> getAll() { return facultyDao.findAll(); }
    public void save(Faculty f) { facultyDao.save(f); }
}
