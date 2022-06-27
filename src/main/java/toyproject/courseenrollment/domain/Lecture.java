package toyproject.courseenrollment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.courseenrollment.domain.valuetype.LectureTime;
import toyproject.courseenrollment.domain.valuetype.MainInfo;
import toyproject.courseenrollment.domain.valuetype.SubInfo;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id @GeneratedValue
    @Column(name = "lecture_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Embedded
    private MainInfo mainInfo;

    @Embedded
    private SubInfo subInfo;

    @Embedded
    private LectureTime lectureTime;

    private int applicant;

    public Lecture(Professor professor, MainInfo mainInfo, SubInfo subInfo, LectureTime lectureTime) {
        this.professor = professor;
        this.mainInfo = mainInfo;
        this.subInfo = subInfo;
        this.lectureTime = lectureTime;
        applicant = 0;
    }

    //==비즈니스 로직==//
    public void updateSubInfo(SubInfo subInfo) {
        this.subInfo = subInfo;
    }

    public void addApplicant() {
        applicant++;
    }

    public void downApplicant() {
        applicant--;
    }

    public boolean isFull() {
        return applicant >= subInfo.getCount();
    }
}
