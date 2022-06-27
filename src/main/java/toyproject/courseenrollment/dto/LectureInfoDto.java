package toyproject.courseenrollment.dto;

import lombok.Data;
import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.valuetype.LectureTime;
import toyproject.courseenrollment.domain.valuetype.MainInfo;
import toyproject.courseenrollment.domain.valuetype.SubInfo;

@Data
public class LectureInfoDto {

    private Long lectureId;
    private Long professorId;

    private MainInfoDto mainInfoDto;
    private SubInfoDto subInfoDto;
    private LectureTimeDto lectureTimeDto;

    private String professorName;

    public LectureInfoDto(Long lectureId, Long professorId, MainInfoDto mainInfoDto, SubInfoDto subInfoDto, LectureTimeDto lectureTimeDto) {
        this.lectureId = lectureId;
        this.professorId = professorId;
        this.mainInfoDto = mainInfoDto;
        this.subInfoDto = subInfoDto;
        this.lectureTimeDto = lectureTimeDto;
    }

    public LectureInfoDto(Long lectureId, MainInfoDto mainInfoDto, SubInfoDto subInfoDto, LectureTimeDto lectureTimeDto, String professorName) {
        this.lectureId = lectureId;
        this.mainInfoDto = mainInfoDto;
        this.subInfoDto = subInfoDto;
        this.lectureTimeDto = lectureTimeDto;
        this.professorName = professorName;
    }

    public LectureInfoDto(Lecture lecture) {
        this.lectureId = lecture.getId();
        this.mainInfoDto = getMainInfoDto(lecture.getMainInfo());
        this.subInfoDto = getSubInfoDto(lecture.getSubInfo());
        this.lectureTimeDto = getLectureTimeDto(lecture.getLectureTime());
        this.professorName = lecture.getProfessor().getName();
    }

    public LectureInfoDto(MainInfoDto mainInfoDto, SubInfoDto subInfoDto, LectureTimeDto lectureTimeDto) {
        this.mainInfoDto = mainInfoDto;
        this.subInfoDto = subInfoDto;
        this.lectureTimeDto = lectureTimeDto;
    }

    public LectureInfoDto(MainInfoDto mainInfoDto, SubInfoDto subInfoDto, LectureTimeDto lectureTimeDto, String professorName) {
        this.mainInfoDto = mainInfoDto;
        this.subInfoDto = subInfoDto;
        this.lectureTimeDto = lectureTimeDto;
        this.professorName = professorName;
    }

    private MainInfoDto getMainInfoDto(MainInfo mainInfo) {
        return new MainInfoDto(mainInfo.getOpenYear(), mainInfo.getSemester(), mainInfo.getMajor(), mainInfo.getCourseId(), mainInfo.getCourseClass(), mainInfo.getCourseTitle(), mainInfo.getCompletionType(), mainInfo.getCredit(), mainInfo.getTheory(), mainInfo.getLab(), mainInfo.getYear());
    }

    private SubInfoDto getSubInfoDto(SubInfo subInfo) {
        return new SubInfoDto(subInfo.getLanguage(), subInfo.getRoom(), subInfo.getMidExamRate(), subInfo.getFinalExamRate(), subInfo.getTaskRate(), subInfo.getAttendanceRate(), subInfo.getCount());
    }

    private LectureTimeDto getLectureTimeDto(LectureTime lectureTime) {
        return new LectureTimeDto(lectureTime.getDay(), lectureTime.getStartTime(), lectureTime.getEndTime());
    }
}
