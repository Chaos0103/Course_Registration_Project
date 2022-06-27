package toyproject.courseenrollment.repository.custom;

import toyproject.courseenrollment.domain.Course;

import java.util.Optional;

public interface CourseRepositoryCustom {

    Optional<Course> findByDayAndTime(String day, String time);

//    Optional<Course> findDuplicate(Long studentId, String courseId);
}
