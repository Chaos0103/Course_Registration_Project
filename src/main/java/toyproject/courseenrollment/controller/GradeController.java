package toyproject.courseenrollment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.courseenrollment.controller.form.GradeForm;
import toyproject.courseenrollment.controller.form.LectureSearch;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.dto.CourseInfoDto;
import toyproject.courseenrollment.dto.GradeInfoDto;
import toyproject.courseenrollment.dto.LectureInfoDto;
import toyproject.courseenrollment.service.CourseService;
import toyproject.courseenrollment.service.GradeService;
import toyproject.courseenrollment.service.LectureService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;
    private final LectureService lectureService;
    private final CourseService courseService;

    // ModelAttribute
    @ModelAttribute("completions")
    public CompletionType[] completionTypes() {
        return CompletionType.values();
    }

    @GetMapping("/grade")
    public String findMyLecture(@ModelAttribute("searchForm") LectureSearch search, Model model, HttpSession session) {
        Long professorId = (Long) session.getAttribute("professorId");
        List<LectureInfoDto> lectureList = lectureService.findLectureByProfessor(professorId);
        model.addAttribute("lectureList", lectureList);
        return "/grade/lectureList";
    }

    @GetMapping("/grade/{lectureId}/studentList")
    public String studentList(@PathVariable Long lectureId, Model model) {
        List<CourseInfoDto> courseList = courseService.searchStudentList(lectureId);
        model.addAttribute("courseList", courseList);
        return "/grade/studentList";
    }

    @GetMapping("/grade/{gradeId}/input")
    public String getInputGrade(@ModelAttribute("gradeForm") GradeForm gradeForm, @PathVariable Long gradeId, Model model) {
        return "/grade/createGradeForm";
    }

    @PostMapping("/grade/{gradeId}/input")
    public String postInputGrade(GradeForm form, @PathVariable Long gradeId) {
        GradeInfoDto gradeInfoDto = new GradeInfoDto(gradeId, null, form.getMidExam(), form.getFinalExam(), form.getTask(), form.getAttendance());
        gradeService.insertGrade(gradeInfoDto);
        return "redirect:/grade";
    }

    @GetMapping("/nowGrade")
    public String noGrade(Model model, HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        List<CourseInfoDto> nowGradeList = gradeService.findNowGrade(studentId);
        model.addAttribute("nowGradeList", nowGradeList);
        return "/student/nowGrade";
    }

    @GetMapping("/prevGrade")
    public String prevGrade(Model model, HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        List<CourseInfoDto> prevGradeList = gradeService.findPrevGrade(studentId);
        model.addAttribute("prevGradeList", prevGradeList);
        return "/student/prevGrade";
    }
}
