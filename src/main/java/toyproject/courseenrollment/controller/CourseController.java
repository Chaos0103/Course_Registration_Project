package toyproject.courseenrollment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.courseenrollment.controller.form.LectureSearch;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.dto.CourseInfoDto;
import toyproject.courseenrollment.dto.LectureInfoDto;
import toyproject.courseenrollment.service.CourseService;
import toyproject.courseenrollment.service.LectureService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CourseController {

    private final LectureService lectureService;
    private final CourseService courseService;

    // ModelAttribute
    @ModelAttribute("completions")
    public CompletionType[] completionTypes() {
        return CompletionType.values();
    }

    @ModelAttribute("majors")
    public Major[] majorTypes() {
        return Major.values();
    }

    @GetMapping("/courses/new")
    public String course(@ModelAttribute("searchForm") LectureSearch lectureSearch, Model model, HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        List<LectureInfoDto> lectures = lectureService.search(lectureSearch.getCompletionType(), lectureSearch.getLectureName(), lectureSearch.getProfessorName());
        List<CourseInfoDto> courseList = courseService.search(studentId);
        model.addAttribute("lectures", lectures);
        model.addAttribute("courseList", courseList);
        return "/student/course";
    }

    @PostMapping("/course/{lectureId}/input")
    public String inputCourse(@PathVariable Long lectureId, HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        courseService.createCourse(studentId, lectureId);
        return "redirect:/courses/new";
    }

    @PostMapping("/course/{courseId}/delete")
    public String deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return "redirect:/courses/new";
    }
}
