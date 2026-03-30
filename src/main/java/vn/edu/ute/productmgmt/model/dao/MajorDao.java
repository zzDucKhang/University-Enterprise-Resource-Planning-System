package vn.edu.ute.productmgmt.model.dao;

import vn.edu.ute.productmgmt.model.entity.Major;

// Ngành học
public class MajorDao extends BaseDao<Major, Long> {
    public MajorDao() {
        super(Major.class);
    }
}