package toyproject.courseenrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final ProfessorRepository professorRepository;

    /**
     * 강의등록
     */
    public Long createLecture(LectureInfoDto infoDto, Long professorId) {

        Optional<Professor> findProfessor = professorRepository.findById(professorId);

        if (findProfessor.isEmpty()) {
            throw new IllegalStateException();
        }

        duplicatedLecture(infoDto.getMainInfoDto().getCourseId(), infoDto.getMainInfoDto().getCourseClass());

        MainInfoDto mainInfoDto = infoDto.getMainInfoDto();
        MainInfo mainInfo = new MainInfo(mainInfoDto.getOpenYear(), mainInfoDto.getSemester(), mainInfoDto.getMajor(), mainInfoDto.getCourseId(), mainInfoDto.getCourseClass(), mainInfoDto.getCourseTitle(), mainInfoDto.getCompletionType(),
                mainInfoDto.getCredit(), mainInfoDto.getTheory(), mainInfoDto.getLab(), mainInfoDto.getYear());

        SubInfoDto subInfoDto = infoDto.getSubInfoDto();
        SubInfo subInfo = new SubInfo(subInfoDto.getLanguage(), subInfoDto.getRoom(), subInfoDto.getMidExamRate(),
                subInfoDto.getFinalExamRate(), subInfoDto.getTaskRate(), subInfoDto.getAttendanceRate(), subInfoDto.getCount());

        LectureTimeDto lectureTimeDto = infoDto.getLectureTimeDto();
        LectureTime lectureTime = new LectureTime(lectureTimeDto.getDay(), lectureTimeDto.getStartTime(), lectureTimeDto.getEndTime());

        Lecture savedLecture = lectureRepository.save(new Lecture(findProfessor.get(), mainInfo, subInfo, lectureTime));
        return savedLecture.getId();
    }

    /**
     * 강의수정
     */
    public void updateLecture(Long lectureId, SubInfoDto infoDto) {
        Optional<Lecture> findLecture = lectureRepository.findById(lectureId);
        if (findLecture.isEmpty()) {
            throw new IllegalStateException();
        }
        SubInfo subInfo = new SubInfo(infoDto.getLanguage(), infoDto.getRoom(), infoDto.getMidExamRate(), infoDto.getFinalExamRate(), infoDto.getTaskRate(), infoDto.getAttendanceRate(), infoDto.getCount());
        findLecture.get().updateSubInfo(subInfo);
    }

    /**
     * 강의삭제
     */
    public void deleteLecture(Long lectureId, Long professorId) {

        Optional<Lecture> findLecture = lectureRepository.findById(lectureId);

        if (findLecture.isPresent()) {
            Lecture lecture = findLecture.get();
            if (lecture.getProfessor().getId().equals(professorId)) {
                lectureRepository.delete(lecture);
            }
        } else {
            throw new NoSuchElementException("존재하지 않는 강의입니다.");
        }
    }

    /**
     * 강의조회
     */
    public List<LectureInfoDto> search(CompletionType type, String lectureName, String professorName) {
        List<Lecture> lectureList = lectureRepository.findSearch(type, lectureName, professorName);
        return EntityToDto(lectureList);
    }

    public List<LectureInfoDto> findLectureByProfessor(Long professorId) {
        List<Lecture> lectureList = lectureRepository.findLectureByProfessor(professorId);
        return EntityToDto(lectureList);
    }

    private List<LectureInfoDto> EntityToDto(List<Lecture> lectures) {
        return lectures.stream()
                .map(LectureInfoDto::new).toList();
    }

    //중복검사
    private void duplicatedLecture(String courseId, String courseClass) {
        Optional<Lecture> findLecture = lectureRepository.findByMainInfoCourseIdAndMainInfoCourseClass(courseId, courseClass);
        if (findLecture.isPresent()) {
            throw new DuplicateException("이미 등록된 강의입니다.");
        }
    }
}
