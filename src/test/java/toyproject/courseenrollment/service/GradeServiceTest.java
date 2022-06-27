package toyproject.courseenrollment.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Course;
import toyproject.courseenrollment.domain.Grade;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.dto.GradeInfoDto;
import toyproject.courseenrollment.repository.GradeRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GradeServiceTest {

    @Autowired GradeService gradeService;
    @Autowired GradeRepository gradeRepository;

    @Test
    @DisplayName("성적입력")
    @Rollback(value = false)
    void insertGrade() {
        //given
        Grade grade = new Grade();

        //when

        //then


    }
}