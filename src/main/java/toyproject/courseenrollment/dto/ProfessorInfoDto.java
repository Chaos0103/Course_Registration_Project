package toyproject.courseenrollment.dto;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.Major;

@Data
public class ProfessorInfoDto {

    private Long id;
    private String name;
    private String password;
    private Major major;
    private String email;
    private String office;

    public ProfessorInfoDto(Long id, String name, String password, Major major, String email, String office) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.major = major;
        this.email = email;
        this.office = office;
    }
}
