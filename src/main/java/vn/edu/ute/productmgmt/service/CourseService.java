package vn.edu.ute.productmgmt.service;

import vn.edu.ute.productmgmt.model.dao.CourseDao;
import vn.edu.ute.productmgmt.model.entity.Course;
import java.util.List;

public class CourseService {
    private final CourseDao courseDao = new CourseDao();

    public List<Course> getAllWithPrerequisites() {
        return courseDao.findAllWithPrerequisites();
    }

    public String saveCourse(Course course) {
        if (course.getCredits() <= 0) return "Số tín chỉ phải lớn hơn 0";
        courseDao.save(course);
        return "SUCCESS";
    }

    public void addPrerequisite(Long courseId, Long prerequisiteId) {
        Course course = courseDao.findById(courseId);
        Course pre = courseDao.findById(prerequisiteId);
        if (course != null && pre != null && !courseId.equals(prerequisiteId)) {
            course.getPrerequisites().add(pre);
            courseDao.save(course);
        }
    }
}