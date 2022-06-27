package toyproject.courseenrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Interest;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.dto.InterestInfoDto;
import toyproject.courseenrollment.repository.InterestRepository;
import toyproject.courseenrollment.repository.LectureRepository;
import toyproject.courseenrollment.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    /**
     * 관심과목등록
     */
    @Transactional
    public Long createInterest(Long studentId, Long lectureId) {
        Student student = findStudent(studentId);
        Lecture lecture = findLecture(lectureId);

        duplicatedInterest(student, lecture);

        Interest savedInterest = interestRepository.save(new Interest(student, lecture));
        return savedInterest.getId();
    }

    /**
     * 관심과목삭제
     */
    @Transactional
    public void deleteInterest(Long interestId) {
        interestRepository.deleteById(interestId);
    }

    /**
     * 관심과목조회
     */
    public List<InterestInfoDto> findAllByStudentId(Long studentId) {
        List<InterestInfoDto> result = new ArrayList<>();
        List<Interest> interests = interestRepository.findByStudentId(studentId);
        for (Interest interest : interests) {
            result.add(new InterestInfoDto(interest));
        }
        return result;
    }

    //학생찾기
    private Student findStudent(Long studentId) {
        Optional<Student> findStudent = studentRepository.findById(studentId);
        if (findStudent.isEmpty()) {
            throw new NoSuchElementException("등록되지 않은 학생입니다.");
        }
        return findStudent.get();
    }

    //강의찾기
    private Lecture findLecture(Long lectureId) {
        Optional<Lecture> findLecture = lectureRepository.findById(lectureId);
        if (findLecture.isEmpty()) {
            throw new NoSuchElementException("등록되지 않은 강의입니다.");
        }
        return findLecture.get();
    }

    //중복검사
    private void duplicatedInterest(Student student, Lecture lecture) {
        Optional<Interest> findInterest = interestRepository.findInterest(student.getId(), lecture.getMainInfo().getCourseId());
        if (findInterest.isPresent()) {
            throw new IllegalStateException();
        }
    }
}
