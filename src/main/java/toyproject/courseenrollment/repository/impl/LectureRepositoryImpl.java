package toyproject.courseenrollment.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.QLecture;
import toyproject.courseenrollment.domain.QProfessor;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.repository.custom.LectureRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static toyproject.courseenrollment.domain.QLecture.*;
import static toyproject.courseenrollment.domain.QProfessor.professor;

public class LectureRepositoryImpl implements LectureRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LectureRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Lecture> findSearch(CompletionType type, String lectureName, String professorName) {
        return queryFactory
                .selectFrom(lecture)
                .join(lecture.professor, professor)
                .fetchJoin()
                .where(
                        completionEq(type),
                        lectureNameEq(lectureName),
                        professorNameEq(professorName)
                )
                .fetch();
    }

    private Predicate lectureNameEq(String lectureName) {
        return hasText(lectureName) ? lecture.mainInfo.courseTitle.eq(lectureName) : null;
    }

    private Predicate professorNameEq(String professorName) {
        return hasText(professorName) ? lecture.professor.name.eq(professorName) : null;
    }

    private BooleanExpression completionEq(CompletionType type) {
        return type != null ? lecture.mainInfo.completionType.eq(type) : null;
    }
}
