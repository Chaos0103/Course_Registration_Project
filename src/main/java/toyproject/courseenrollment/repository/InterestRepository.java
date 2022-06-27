package toyproject.courseenrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toyproject.courseenrollment.domain.Interest;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    @Query("select i from Interest i join fetch i.lecture where i.student.id = :studentId")
    List<Interest> findByStudentId(@Param("studentId") Long studentId);

    @Query("select i from Interest i where i.student.id = :studentId and i.lecture.mainInfo.courseId = :courseId")
    Optional<Interest> findInterest(@Param("studentId") Long studentId, @Param("courseId") String courseId);
}
