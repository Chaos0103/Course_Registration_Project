package toyproject.courseenrollment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Interest;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Language;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.domain.enumtype.SexType;
import toyproject.courseenrollment.domain.valuetype.LectureTime;
import toyproject.courseenrollment.domain.valuetype.MainInfo;
import toyproject.courseenrollment.domain.valuetype.SubInfo;
import toyproject.courseenrollment.repository.InterestRepository;
import toyproject.courseenrollment.repository.LectureRepository;
import toyproject.courseenrollment.repository.ProfessorRepository;
import toyproject.courseenrollment.repository.StudentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InterestServiceTest {

    @Autowired InterestService interestService;
    @Autowired InterestRepository interestRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired LectureRepository lectureRepository;
    @Autowired ProfessorRepository professorRepository;

    @Test
    @DisplayName("관심과목등록")
    void createInterest() {
        //given
        Student student = getStudent();
        Lecture lecture = getLecture();

        //when
        Long interestId = interestService.createInterest(student.getId(), lecture.getId());

        //then
        Optional<Interest> findInterest = interestRepository.findById(interestId);
        assertThat(findInterest).isPresent();
    }

    @Test
    @DisplayName("관심과목삭제")
    void deleteInterest() {
        //given
        Interest savedInterest = interestRepository.save(new Interest(null, null));

        //when
        interestService.deleteInterest(savedInterest.getId());

        //then
        Optional<Interest> findInterest = interestRepository.findById(savedInterest.getId());
        assertThat(findInterest).isEmpty();
    }

    private Student getStudent() {
        return studentRepository.save(new Student(22001123L, "student", "1234", Major.COMPUTER, 1, "2022-01-01", SexType.MALE));
    }

    private Lecture getLecture() {
        MainInfo mainInfo = new MainInfo(2022, 1, Major.COMPUTER, "009912", "001", "C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
        SubInfo subInfo = new SubInfo(Language.KOREAN, "센B201", 50, 20, 20, 10, 40);
        LectureTime lectureTime = new LectureTime("화", 1630, 1830);
        Lecture lecture = new Lecture(getProfessor(), mainInfo, subInfo, lectureTime);
        return lectureRepository.save(lecture);
    }

    private Professor getProfessor() {
        return professorRepository.save(new Professor("교수", "1234", Major.COMPUTER, "abc123@naver.com", "센800"));
    }
}