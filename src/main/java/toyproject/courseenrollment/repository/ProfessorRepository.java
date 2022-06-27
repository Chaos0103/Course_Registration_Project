package toyproject.courseenrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.repository.custom.ProfessorRepositoryCustom;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorRepositoryCustom, QuerydslPredicateExecutor<Professor> {

    @Query("select p from Professor p where p.email = :email")
    Optional<Professor> findLogin(@Param("email") String email);
}
