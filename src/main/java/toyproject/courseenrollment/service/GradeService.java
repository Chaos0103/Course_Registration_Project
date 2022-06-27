package toyproject.courseenrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Course;
import toyproject.courseenrollment.domain.Grade;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.dto.CourseInfoDto;
import toyproject.courseenrollment.dto.GradeInfoDto;
import toyproject.courseenrollment.repository.CourseRepository;
import toyproject.courseenrollment.repository.GradeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;

    /**
     * 성적등록
     */
    @Transactional
    public Long insertGrade(GradeInfoDto infoDto) {
        Grade grade = getGrade(infoDto);
        Course course = findCourse(grade.getCourse().getId());
        grade.insertGrade(infoDto.getMidExam(), infoDto.getFinalExam(), infoDto.getTask(), infoDto.getAttendance(), getScore(infoDto, course.getLecture()));
        return grade.getId();
    }

    private Grade getGrade(GradeInfoDto infoDto) {
        Optional<Grade> findGrade = gradeRepository.findById(infoDto.getGradeId());
        if (findGrade.isEmpty()) {
            throw new IllegalStateException();
        }
        return findGrade.get();
    }


    //성적정정
    //성적삭제

    /**
     * 성적조회
     */
    public List<CourseInfoDto> findNowGrade(Long studentId) {
        List<Course> courseList = courseRepository.findNowGrade(studentId, LocalDateTime.now().getYear());
        return courseList.stream()
                .map(c -> new CourseInfoDto(c.getLecture(), c.getGrade())).toList();
    }

    public List<CourseInfoDto> findPrevGrade(Long studentId) {
        List<Course> courseList = courseRepository.findPrevGrade(studentId, LocalDateTime.now().getYear());
        return courseList.stream()
                .map(c -> new CourseInfoDto(c.getLecture(), c.getGrade())).toList();
    }

    private Course findCourse(Long courseId) {
        Optional<Course> findCourse = courseRepository.findCourseLectureGrade(courseId);
        if (findCourse.isEmpty()) {
            throw new NoSuchElementException("해당하는 강의가 없습니다.");
        }
        return findCourse.get();
    }

    private float getScore(GradeInfoDto grade, Lecture lecture) {
        float minExam = grade.getMidExam() / 100 * lecture.getSubInfo().getMidExamRate();
        float finalExam = grade.getFinalExam() / 100 * lecture.getSubInfo().getFinalExamRate();
        float task = grade.getTask() / 100 * lecture.getSubInfo().getTaskRate();
        float attendance = grade.getAttendance() / 100 * lecture.getSubInfo().getAttendanceRate();
        return minExam + finalExam + task + attendance;
    }
}
