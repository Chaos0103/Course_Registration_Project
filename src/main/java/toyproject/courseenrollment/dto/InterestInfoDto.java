package toyproject.courseenrollment.dto;

import lombok.Data;
import toyproject.courseenrollment.domain.Interest;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.valuetype.MainInfo;
import toyproject.courseenrollment.domain.valuetype.SubInfo;

@Data
public class InterestInfoDto {

    private Long interestId;
    private Long studentId;

    private LectureInfoDto lectureInfoDto;

    public InterestInfoDto(Interest interest) {
        this.lectureInfoDto = new LectureInfoDto(interest.getLecture());
    }
}
