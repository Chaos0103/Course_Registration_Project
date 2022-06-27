package toyproject.courseenrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Course;
import toyproject.courseenrollment.domain.Grade;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.dto.CourseInfoDto;
import toyproject.courseenrollment.dto.GradeInfoDto;
import toyproject.courseenrollment.dto.LectureInfoDto;
import toyproject.courseenrollment.exception.CourseException;
import toyproject.courseenrollment.exception.DuplicateException;
import toyproject.courseenrollment.repository.CourseRepository;
import toyproject.courseenrollment.repository.LectureRepository;
import toyproject.courseenrollment.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    /**
     * 수강등록
     */
    @Transactional
    public Long createCourse(Long studentId, Long lectureId) {
        Student student = findStudent(studentId);
        Lecture lecture = findLecture(lectureId);

        Optional<Course> findCourse = courseRepository.findCourse(student.getId(), lecture.getMainInfo().getCourseId());

        if (findCourse.isPresent()) {
            Course course = findCourse.get();
            duplicatedCourse(lecture, course);
            checkedCourse(studentId, lecture);
            course.reRegistration(lecture);
            return course.getId();
        } else {
            checkedCourse(studentId, lecture);
            Course savedCourse = courseRepository.save(new Course(student, lecture, new Grade()));
            return savedCourse.getId();
        }
    }

    /**
     * 수강취소
     */
    @Transactional
    public void deleteCourse(Long courseId) {
        Optional<Course> findCourse = courseRepository.findById(courseId);
        if (findCourse.isEmpty()) {
            throw new IllegalStateException();
        }
        courseRepository.delete(findCourse.get());
    }

    /**
     * 수강조회
     */
    public List<CourseInfoDto> search(Long studentId) {
        List<Course> courseList = courseRepository.findCourseLecture(studentId);
        return courseList.stream()
                .map(CourseInfoDto::new).toList();
    }

    public List<CourseInfoDto> searchStudentList(Long lectureId) {
        List<Course> studentList = courseRepository.findStudentList(lectureId);
        return studentList.stream()
                .map(s -> new CourseInfoDto(s, s.getStudent())).toList();
    }

    public CourseInfoDto findById(Long courseId) {
        Optional<Course> findCourse = courseRepository.findById(courseId);
        return new CourseInfoDto(findCourse.get(), new GradeInfoDto());
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

    private void duplicatedCourse(Lecture lecture, Course course) {
        if (course.getLecture().getMainInfo().getOpenYear() == lecture.getMainInfo().getOpenYear() &&
                course.getLecture().getMainInfo().getSemester() == lecture.getMainInfo().getSemester()) {
            throw new DuplicateException("이미 수강중인 강의입니다.");
        }
    }

    //인원수검사, 시간검사
    private void checkedCourse(Long studentId, Lecture lecture) {
        //인원수검사
        if (lecture.isFull()) {
            throw new CourseException("수강인원이 가득찼습니다.");
        }
        //시간중복검사
        List<Course> courses = courseRepository.findCourseLecture(studentId);
        for (Course courseData : courses) {
            Lecture checkLecture = courseData.getLecture();
            if (checkLecture.getMainInfo().getOpenYear() == lecture.getMainInfo().getOpenYear()
                    && checkLecture.getMainInfo().getSemester() == lecture.getMainInfo().getSemester()) {
                if (checkLecture.getLectureTime().getDay().equals(lecture.getLectureTime().getDay())) {
                    if (checkLecture.getLectureTime().getStartTime() <= lecture.getLectureTime().getStartTime()
                            && lecture.getLectureTime().getStartTime() <= checkLecture.getLectureTime().getEndTime()) {
                        throw new DuplicateException("동시간에 신청된 강의가 있습니다.");
                    } else if (checkLecture.getLectureTime().getStartTime() <= lecture.getLectureTime().getEndTime()
                            && lecture.getLectureTime().getEndTime() <= checkLecture.getLectureTime().getEndTime()) {
                        throw new DuplicateException("동시간에 신청된 강의가 있습니다.");
                    }
                }
            }
        }
    }
}
