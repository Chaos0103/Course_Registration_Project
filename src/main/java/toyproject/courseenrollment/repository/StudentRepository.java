package toyproject.courseenrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.repository.custom.StudentRepositoryCustom;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom, QuerydslPredicateExecutor<Student> {
}
