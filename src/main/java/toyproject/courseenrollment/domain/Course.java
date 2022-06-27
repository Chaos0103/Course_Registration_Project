package toyproject.courseenrollment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.courseenrollment.domain.enumtype.RegistrationType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private RegistrationType registration;

    private LocalDateTime createdDate;

    public Course(Student student, Lecture lecture, Grade grade) {
        this.student = student;
        this.lecture = lecture;
        this.registration = RegistrationType.NONE;
        this.createdDate = LocalDateTime.now();
        this.grade = grade;
        lecture.addApplicant();
    }

    //==비즈니스 로직==//
    public void reRegistration(Lecture lecture) {
        this.lecture = lecture;
        this.registration = RegistrationType.RE;
        this.createdDate = LocalDateTime.now();
        this.grade = new Grade();
        lecture.addApplicant();
    }
    // 성적 업데이트
}
