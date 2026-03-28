package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.SemesterDao;
import vn.edu.ute.productmgmt.model.entity.Semester;

import java.util.List;

public class SemesterService {
    private final SemesterDao semesterDao = new SemesterDao();
    public List<Semester> getAll() { return semesterDao.findAll(); }
    public void save(Semester s) { semesterDao.save(s); }
}