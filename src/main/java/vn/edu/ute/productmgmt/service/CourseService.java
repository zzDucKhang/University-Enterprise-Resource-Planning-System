package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.CourseDao;
import vn.edu.ute.productmgmt.model.entity.Course;
import java.util.List;

public class CourseService {
    private final CourseDao courseDao = new CourseDao();

    // Lấy tất cả các môn học kèm với môn tiên quyết
    public List<Course> getAllWithPrerequisites() {
        return courseDao.findAllWithPrerequisites();
    }


}