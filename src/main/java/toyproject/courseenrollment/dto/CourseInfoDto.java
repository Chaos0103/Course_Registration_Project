package toyproject.courseenrollment.dto;

import lombok.Data;
import toyproject.courseenrollment.domain.Course;
import toyproject.courseenrollment.domain.Grade;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.Student;

@Data
public class CourseInfoDto {

    private Long courseId;
    private Long gradeId;

    private StudentInfoDto studentInfoDto;
    private LectureInfoDto lectureInfoDto;
    private GradeInfoDto gradeInfoDto;

    public CourseInfoDto(Course course) {
        this.courseId = course.getId();
        this.lectureInfoDto = new LectureInfoDto(course.getLecture());
    }

    public CourseInfoDto(Course course, GradeInfoDto gradeInfoDto) {
        this.courseId = course.getId();
        this.gradeInfoDto = gradeInfoDto;
    }

    public CourseInfoDto(Course course, Student student) {
        this.courseId = course.getId();
        this.gradeId = course.getGrade().getId();
        this.studentInfoDto = new StudentInfoDto(student);
    }

    public CourseInfoDto(Lecture lecture, Grade grade) {
        this.lectureInfoDto = new LectureInfoDto(lecture);
        this.gradeInfoDto = new GradeInfoDto(grade);
    }
}
