package vn.edu.ute.productmgmt.model.dao;

import vn.edu.ute.productmgmt.model.entity.Major;

public class MajorDao extends BaseDao<Major, Long> {
    public MajorDao() {
        super(Major.class);
    }
}