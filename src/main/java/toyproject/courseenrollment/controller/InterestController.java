package toyproject.courseenrollment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.courseenrollment.controller.form.LectureSearch;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.dto.InterestInfoDto;
import toyproject.courseenrollment.dto.LectureInfoDto;
import toyproject.courseenrollment.service.InterestService;
import toyproject.courseenrollment.service.LectureService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class InterestController {

    private final LectureService lectureService;
    private final InterestService interestService;

    // ModelAttribute
    @ModelAttribute("completions")
    public CompletionType[] completionTypes() {
        return CompletionType.values();
    }

    @ModelAttribute("majors")
    public Major[] majorTypes() {
        return Major.values();
    }

    @GetMapping("/interest")
    public String interest(@ModelAttribute("searchForm") LectureSearch lectureSearch, Model model) {
        List<LectureInfoDto> lectures = lectureService.search(lectureSearch.getCompletionType(), lectureSearch.getLectureName(), lectureSearch.getProfessorName());
        model.addAttribute("lectures", lectures);
        return "/student/interest";
    }

    @PostMapping("/interest/{lectureId}/input")
    public String input(@PathVariable Long lectureId, HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        interestService.createInterest(studentId, lectureId);
        return "redirect:/interest";
    }

    @GetMapping("/interestList")
    public String interestList(Model model, HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        List<InterestInfoDto> interestList = interestService.findAllByStudentId(studentId);
        model.addAttribute("interestList", interestList);
        return "/student/interestList";
    }
}
