package toyproject.courseenrollment.dto;

import lombok.Data;

@Data
public class LectureTimeDto {

    private String day;
    private int startTime;
    private int endTime;

    public LectureTimeDto(String day, int startTime, int endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
