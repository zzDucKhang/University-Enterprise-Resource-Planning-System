package vn.edu.ute.productmgmt.model.dao;

import vn.edu.ute.productmgmt.model.entity.Faculty;

public class FacultyDao extends BaseDao<Faculty, Long> {
    public FacultyDao() {
        super(Faculty.class);
    }
}