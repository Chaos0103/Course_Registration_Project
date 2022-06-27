package toyproject.courseenrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.courseenrollment.domain.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
