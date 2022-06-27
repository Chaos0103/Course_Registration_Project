package toyproject.courseenrollment.dto;

import lombok.Data;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.domain.enumtype.SexType;

@Data
public class StudentInfoDto {

    private Long id;
    private String name;
    private String password;
    private Major major;
    private int year;
    private String birth;
    private SexType sex;

    public StudentInfoDto(Long id, String name, String password, Major major, int year, String birth, SexType sex) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.major = major;
        this.year = year;
        this.birth = birth;
        this.sex = sex;
    }

    public StudentInfoDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.password = student.getPassword();
        this.major = student.getMajor();
        this.year = student.getYear();
        this.birth = student.getBirth();
        this.sex = student.getSex();
    }
}
