package toyproject.courseenrollment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Language;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.domain.valuetype.LectureTime;
import toyproject.courseenrollment.domain.valuetype.MainInfo;
import toyproject.courseenrollment.domain.valuetype.SubInfo;
import toyproject.courseenrollment.dto.LectureInfoDto;
import toyproject.courseenrollment.dto.LectureTimeDto;
import toyproject.courseenrollment.dto.MainInfoDto;
import toyproject.courseenrollment.dto.SubInfoDto;
import toyproject.courseenrollment.exception.DuplicateException;
import toyproject.courseenrollment.repository.LectureRepository;
import toyproject.courseenrollment.repository.ProfessorRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LectureServiceTest {

    @Autowired LectureService lectureService;
    @Autowired LectureRepository lectureRepository;
    @Autowired ProfessorRepository professorRepository;

    @Test
    @DisplayName("강의등록")
    void createLecture() {
        //given
        Professor professor = getProfessor();

        MainInfoDto mainInfoDto = new MainInfoDto(2022, 1, Major.COMPUTER, "009912", "001", "C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
        SubInfoDto subInfoDto = new SubInfoDto(Language.KOREAN, "센B201", 50, 20, 20, 10, 40);
        LectureTimeDto lectureTimeDto = new LectureTimeDto("화", 1630, 1830);

        LectureInfoDto lectureInfoDto = new LectureInfoDto(null, null, mainInfoDto, subInfoDto, lectureTimeDto);

        //when
        Long lectureId = lectureService.createLecture(lectureInfoDto, professor.getId());

        //then
        Lecture findLecture = lectureRepository.findById(lectureId).get();
        assertThat(findLecture.getId()).isEqualTo(lectureId);
    }

    @Test
    @DisplayName("강의삭제")
    void deleteLecture() {
        //given
        Lecture savedLecture = getLecture();
        Long id = savedLecture.getId();

        //when
        lectureService.deleteLecture(savedLecture.getId(), savedLecture.getProfessor().getId());

        //then
        Optional<Lecture> findLecture = lectureRepository.findById(id);
        assertThat(findLecture).isEmpty();
    }

    @Test
    @DisplayName("강의삭제예외")
    void deleteLectureException() {
        //given
        Professor professor = getProfessor();
        //when
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            lectureService.deleteLecture(12345L, professor.getId());
        });
        //then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 강의입니다.");
    }

    @Test
    @DisplayName("중복검사")
    void duplicatedLecture() {
        //given
        Lecture lecture = getLecture();
        MainInfoDto mainInfoDto = new MainInfoDto(2022, 1, Major.COMPUTER, "009912", "001", "C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
        SubInfoDto subInfoDto = new SubInfoDto(Language.KOREAN, "센B201", 50, 20, 20, 10, 40);
        LectureTimeDto lectureTimeDto = new LectureTimeDto("화", 1630, 1830);

        LectureInfoDto lectureInfoDto = new LectureInfoDto(null, null, mainInfoDto, subInfoDto, lectureTimeDto);

        //when
        DuplicateException duplicateException = assertThrows(DuplicateException.class, () -> {
            lectureService.createLecture(lectureInfoDto, lecture.getProfessor().getId());
        });

        //then
        assertThat(duplicateException.getMessage()).isEqualTo("이미 등록된 강의입니다.");
    }

    @Test
    @DisplayName("강의수정")
    void updateLecture() {
        //given
        Lecture lecture = getLecture();

        //when
        SubInfoDto subInfoDto = new SubInfoDto(Language.KOREAN, "센202", 50, 20, 20, 10, 45);
        lectureService.updateLecture(lecture.getId(), subInfoDto);

        //then
        assertThat(lecture.getSubInfo().getRoom()).isEqualTo("센202");
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