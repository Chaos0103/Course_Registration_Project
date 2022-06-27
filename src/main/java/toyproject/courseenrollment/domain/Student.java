package toyproject.courseenrollment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.domain.enumtype.SexType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @Column(name = "student_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, updatable = false)
    private Major major;

    @Column(nullable = false)
    private int year;

    @Column(length = 10, nullable = false, updatable = false)
    private String birth;

    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, updatable = false)
    private SexType sex;

    public Student(Long id, String name, String password, Major major, int year, String birth, SexType sex) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.major = major;
        this.year = year;
        this.birth = birth;
        this.sex = sex;
    }
}
