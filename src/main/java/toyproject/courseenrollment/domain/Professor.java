package toyproject.courseenrollment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.courseenrollment.domain.enumtype.Major;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor {

    @Id @GeneratedValue
    @Column(name = "professor_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
//    @Column(updatable = false, nullable = false)
    private Major major;

    private String email;
    private String office;

    public Professor(String name, String password, Major major, String email, String office) {
        this.name = name;
        this.password = password;
        this.major = major;
        this.email = email;
        this.office = office;
    }
}
