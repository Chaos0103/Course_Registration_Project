package toyproject.courseenrollment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.courseenrollment.controller.form.LectureForm;
import toyproject.courseenrollment.controller.form.LectureSearch;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.dto.LectureInfoDto;
import toyproject.courseenrollment.dto.LectureTimeDto;
import toyproject.courseenrollment.dto.MainInfoDto;
import toyproject.courseenrollment.dto.SubInfoDto;
import toyproject.courseenrollment.service.LectureService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    // ModelAttribute
    @ModelAttribute("completions")
    public CompletionType[] completionTypes() {
        return CompletionType.values();
    }

    @GetMapping("/lectures/new")
    public String getCreateLecture(@ModelAttribute("lectureForm") LectureForm lectureForm) {
        return "/lecture/createLectureForm";
    }

    @PostMapping("/lectures/new")
    public String postCreateLecture(LectureForm form, HttpSession session) {
        LectureInfoDto lectureInfoDto = getLectureInfoDto(form);
        Long professorId = (Long) session.getAttribute("professorId");
        lectureService.createLecture(lectureInfoDto, professorId);
        return "redirect:/professor";
    }

    @GetMapping("lectures")
    public String lectureList(@ModelAttribute("searchForm") LectureSearch lectureSearch, Model model) {
        List<LectureInfoDto> lectures = lectureService.search(lectureSearch.getCompletionType(), lectureSearch.getLectureName(), lectureSearch.getProfessorName());
        model.addAttribute("lectures", lectures);
        return "/lecture/lectureList";
    }

    private LectureInfoDto getLectureInfoDto(LectureForm form) {
        MainInfoDto mainInfoDto = new MainInfoDto(form.getOpenYear(), form.getSemester(), form.getMajor(), form.getCourseId(), form.getCourseClass(), form.getCourseTitle(), form.getCompletionType(),
                form.getCredit(), form.getTheory(), form.getLab(), form.getYear());
        SubInfoDto subInfoDto = new SubInfoDto(form.getLanguage(), form.getRoom(), form.getMidExamRate(), form.getFinalExamRate(), form.getTaskRate(), form.getFinalExamRate(), form.getCount());
        LectureTimeDto lectureTimeDto = new LectureTimeDto(form.getDay(), form.getStartTime(), form.getEndTime());
        return new LectureInfoDto(mainInfoDto, subInfoDto, lectureTimeDto);
    }
}
