package toyproject.courseenrollment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import toyproject.courseenrollment.controller.form.LectureSearch;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.dto.LectureInfoDto;
import toyproject.courseenrollment.service.LectureService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TimetableController {

    private final LectureService lectureService;

    // ModelAttribute
    @ModelAttribute("completions")
    public CompletionType[] completionTypes() {
        return CompletionType.values();
    }

    @ModelAttribute("majors")
    public Major[] majorTypes() {
        return Major.values();
    }

    @GetMapping("/timetable")
    public String timetable(@ModelAttribute("searchForm") LectureSearch lectureSearch, Model model) {
        List<LectureInfoDto> lectures = lectureService.search(lectureSearch.getCompletionType(), lectureSearch.getLectureName(), lectureSearch.getProfessorName());
        model.addAttribute("lectures", lectures);
        return "/student/timetable";
    }
}
