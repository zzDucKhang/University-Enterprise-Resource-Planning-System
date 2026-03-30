package vn.edu.ute.productmgmt.model.dao;

import vn.edu.ute.productmgmt.model.entity.Semester;

//Học kỳ
public class SemesterDao extends BaseDao<Semester, Long> {
    public SemesterDao() {
        super(Semester.class);
    }
}