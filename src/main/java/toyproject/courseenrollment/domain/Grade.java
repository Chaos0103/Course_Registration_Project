package toyproject.courseenrollment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.courseenrollment.domain.enumtype.GradeType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Grade {

    @Id @GeneratedValue
    @Column(name = "grade_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private GradeType score;

    private float midExam;
    private float finalExam;
    private float task;
    private float attendance;

    private float totalScore;

    @OneToOne(mappedBy = "grade")
    private Course course;

    //==비즈니스 로직==//
    public void insertGrade(float midExam, float finalExam, float task, float attendance, float totalScore) {
        this.midExam = midExam;
        this.finalExam = finalExam;
        this.task = task;
        this.attendance = attendance;
        this.totalScore = totalScore;
        this.score = getScore(totalScore);
    }

    private GradeType getScore(float totalScore) {
        if (95 <= totalScore) {
            return GradeType.AP;
        } else if (90 <= totalScore) {
            return GradeType.AZ;
        } else if (85 <= totalScore) {
            return GradeType.BP;
        } else if (80 <= totalScore) {
            return GradeType.BZ;
        } else if (75 <= totalScore) {
            return GradeType.CP;
        } else if (70 <= totalScore) {
            return GradeType.CZ;
        } else if (65 <= totalScore) {
            return GradeType.DP;
        } else if (60 <= totalScore) {
            return GradeType.DZ;
        } else {
            return GradeType.F;
        }
    }
}
