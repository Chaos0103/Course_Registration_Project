package toyproject.courseenrollment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.*;
import toyproject.courseenrollment.domain.enumtype.*;
import toyproject.courseenrollment.domain.valuetype.LectureTime;
import toyproject.courseenrollment.domain.valuetype.MainInfo;
import toyproject.courseenrollment.domain.valuetype.SubInfo;
import toyproject.courseenrollment.exception.CourseException;
import toyproject.courseenrollment.exception.DuplicateException;
import toyproject.courseenrollment.repository.CourseRepository;
import toyproject.courseenrollment.repository.LectureRepository;
import toyproject.courseenrollment.repository.ProfessorRepository;
import toyproject.courseenrollment.repository.StudentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CourseServiceTest {

    @Autowired CourseService courseService;
    @Autowired CourseRepository courseRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired LectureRepository lectureRepository;
    @Autowired ProfessorRepository professorRepository;

    @Test
    @DisplayName("수강등록")
    void createCourse() {
        //given
        Student student = getStudent();
        Lecture lecture = getLecture();

        //when
        Long courseId = courseService.createCourse(student.getId(), lecture.getId());

        //then
        Course findCourse = courseRepository.findById(courseId).get();
        assertThat(findCourse).isNotNull();
    }

    @Test
    @DisplayName("강의중복")
    void duplicatedCourse() {
        //given
        Student student = getStudent();
        Lecture lecture = getLecture();
        courseRepository.save(new Course(student, lecture, new Grade()));

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> {
            courseService.createCourse(student.getId(), lecture.getId());
        });

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 수강중인 강의입니다.");
    }

    @Test
    @DisplayName("수강인원초과")
    void countFail() {
        //given
        Student studentA = studentRepository.save(new Student(22001123L, "studentA", "123", Major.COMPUTER, 1, "2022-01-01", SexType.MALE));
        Student studentB = studentRepository.save(new Student(22001124L, "studentB", "123", Major.COMPUTER, 1, "2022-02-02", SexType.FEMALE));
        Lecture lecture = getLecture();

        courseRepository.save(new Course(studentA, lecture, new Grade()));

        //when
        CourseException exception = assertThrows(CourseException.class, () -> {
            courseService.createCourse(studentB.getId(), lecture.getId());
        });

        //then
        assertThat(exception.getMessage()).isEqualTo("수강인원이 가득찼습니다.");
    }

    @Test
    @DisplayName("시간중복검사")
    void duplicatedTime() {
        //given
        Student student = getStudent();
        Professor professor = getProfessor();
        MainInfo mainInfoA = new MainInfo(2022, 1, Major.COMPUTER, "009912", "001", "C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
        MainInfo mainInfoB = new MainInfo(2022, 1, Major.COMPUTER, "009913", "001", "고급C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
        SubInfo subInfo = new SubInfo(Language.KOREAN, "센B201", 50, 20, 20, 10, 1);

        LectureTime lectureTimeA = new LectureTime("화", 1630, 1830);
        Lecture lectureA = lectureRepository.save(new Lecture(professor, mainInfoA, subInfo, lectureTimeA));
        LectureTime lectureTimeB = new LectureTime("화", 1730, 1900);
        Lecture lectureB = lectureRepository.save(new Lecture(professor, mainInfoB, subInfo, lectureTimeB));
        courseRepository.save(new Course(student, lectureA, new Grade()));

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> {
            courseService.createCourse(student.getId(), lectureB.getId());
        });

        //then
        assertThat(exception.getMessage()).isEqualTo("동시간에 신청된 강의가 있습니다.");
    }

    @Test
    @DisplayName("재수강등록")
    void recreateCourse() {
        //given
        Student student = getStudent();
        Professor professor = getProfessor();
        MainInfo mainInfoA = new MainInfo(2021, 1, Major.COMPUTER, "009912", "001", "C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
        MainInfo mainInfoB = new MainInfo(2022, 1, Major.COMPUTER, "009912", "001", "C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
        SubInfo subInfo = new SubInfo(Language.KOREAN, "센B201", 50, 20, 20, 10, 1);
        LectureTime lectureTime = new LectureTime("화", 1630, 1830);
        Lecture lectureA = lectureRepository.save(new Lecture(professor, mainInfoA, subInfo, lectureTime));
        Lecture lectureB = lectureRepository.save(new Lecture(professor, mainInfoB, subInfo, lectureTime));
        courseRepository.save(new Course(student, lectureA, new Grade()));

        //when
        Long courseId = courseService.createCourse(student.getId(), lectureB.getId());

        //then
        Optional<Course> findCourse = courseRepository.findById(courseId);
        assertThat(findCourse.get().getRegistration()).isEqualTo(RegistrationType.RE);
    }

    private Student getStudent() {
        return studentRepository.save(new Student(22001123L, "student", "1234", Major.COMPUTER, 1, "2022-01-01", SexType.MALE));
    }

    private Lecture getLecture() {
        MainInfo mainInfo = new MainInfo(2022, 1, Major.COMPUTER, "009912", "001", "C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
        SubInfo subInfo = new SubInfo(Language.KOREAN, "센B201", 50, 20, 20, 10, 1);
        LectureTime lectureTime = new LectureTime("화", 1630, 1830);
        Lecture lecture = new Lecture(getProfessor(), mainInfo, subInfo, lectureTime);
        return lectureRepository.save(lecture);
    }

    private Professor getProfessor() {
        return professorRepository.save(new Professor("교수", "1234", Major.COMPUTER, "abc123@naver.com", "센800"));
    }
}