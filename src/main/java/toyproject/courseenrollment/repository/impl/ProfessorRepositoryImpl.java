package toyproject.courseenrollment.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.repository.custom.ProfessorRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static toyproject.courseenrollment.domain.QProfessor.*;

public class ProfessorRepositoryImpl implements ProfessorRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProfessorRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Professor> findProfessors(String name, Major major) {
        return queryFactory
                .selectFrom(professor)
                .where(
                        nameEq(name),
                        majorEq(major)
                )
                .fetch();
    }

    private Predicate nameEq(String name) {
        return hasText(name) ? professor.name.eq(name) : null;
    }

    private BooleanExpression majorEq(Major major) {
        return major != null ? professor.major.eq(major) : null;
    }
}
