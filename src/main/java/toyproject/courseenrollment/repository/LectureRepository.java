package toyproject.courseenrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.repository.custom.LectureRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom, QuerydslPredicateExecutor<Lecture> {

    Optional<Lecture> findByMainInfoCourseIdAndMainInfoCourseClass(String courseId, String courseClass);

    @Query("select l from Lecture l join fetch l.professor p where p.id = :professorId")
    List<Lecture> findLectureByProfessor(@Param("professorId") Long professorId);
}
