package toyproject.courseenrollment.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.courseenrollment.domain.QStudent;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.repository.custom.StudentRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static toyproject.courseenrollment.domain.QStudent.*;

public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StudentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Student> findStudents(String name, Major major) {
        return queryFactory
                .selectFrom(student)
                .where(
                        nameEq(name),
                        majorEq(major)
                )
                .fetch();
    }

    private Predicate nameEq(String name) {
        return hasText(name) ? student.name.eq(name) : null;
    }

    private BooleanExpression majorEq(Major major) {
        return major != null ? student.major.eq(major) : null;
    }
}
