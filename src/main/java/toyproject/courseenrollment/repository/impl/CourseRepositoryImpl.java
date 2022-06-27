package toyproject.courseenrollment.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.courseenrollment.domain.Course;
import toyproject.courseenrollment.domain.QCourse;
import toyproject.courseenrollment.repository.custom.CourseRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CourseRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Course> findByDayAndTime(String day, String time) {
        return Optional.empty();
    }

}
