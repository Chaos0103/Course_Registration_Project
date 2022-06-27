package toyproject.courseenrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import toyproject.courseenrollment.domain.Course;
import toyproject.courseenrollment.repository.custom.CourseRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom, QuerydslPredicateExecutor<Course> {

    @Query("select c from Course c where c.student.id = :studentId and c.lecture.mainInfo.courseId = :courseId")
    Optional<Course> findCourse(@Param("studentId") Long studentId, @Param("courseId") String courseId);

    @Query("select c from Course c join fetch c.lecture l where c.student.id = :studentId")
    List<Course> findCourseLecture(@Param("studentId") Long studentId);

    @Query("select c from Course c join fetch c.lecture l join fetch c.grade g where c.id = :courseId")
    Optional<Course> findCourseLectureGrade(@Param("courseId") Long courseId);

    @Query("select c from Course c join fetch c.student s where c.lecture.id = :lectureId")
    List<Course> findStudentList(@Param("lectureId") Long lectureId);

    @Query("select c from Course c join fetch c.student join fetch c.lecture l join fetch c.grade where c.student.id = :studentId and l.mainInfo.openYear = :openYear")
    List<Course> findNowGrade(@Param("studentId") Long studentId, @Param("openYear") int openYear);

    @Query("select c from Course c join fetch c.student join fetch c.lecture l join fetch c.grade where c.student.id = :studentId and l.mainInfo.openYear < :openYear")
    List<Course> findPrevGrade(@Param("studentId") Long studentId, @Param("openYear") int openYear);
}
