package toyproject.courseenrollment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.courseenrollment.controller.form.ProfessorForm;
import toyproject.courseenrollment.controller.form.SearchForm;
import toyproject.courseenrollment.controller.form.StudentForm;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.domain.enumtype.SexType;
import toyproject.courseenrollment.dto.ProfessorInfoDto;
import toyproject.courseenrollment.dto.StudentInfoDto;
import toyproject.courseenrollment.service.ProfessorService;
import toyproject.courseenrollment.service.StudentService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final StudentService studentService;
    private final ProfessorService professorService;

    // ModelAttribute
    @ModelAttribute("majors")
    public Major[] majorTypes() {
        return Major.values();
    }

    @ModelAttribute("sexes")
    public SexType[] sexTypes() {
        return SexType.values();
    }

    @GetMapping("/students/new")
    public String getJoinStudent(@ModelAttribute("studentForm") StudentForm studentForm, Model model) {
        return "/member/createStudentForm";
    }

    @PostMapping("/students/new")
    public String postJoinStudent(StudentForm form) {
        StudentInfoDto studentInfoDto = new StudentInfoDto(form.getStudentId(), form.getName(), form.getPassword(), form.getMajor(), form.getYear(), form.getBirth(), form.getSex());
        studentService.join(studentInfoDto);
        return "redirect:/";
    }

    @GetMapping("/students")
    public String getStudentList(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
        List<StudentInfoDto> students = studentService.findStudents(searchForm.getStudentName(), searchForm.getMajor());
        model.addAttribute("students", students);
        return "/member/studentList";
    }

    @GetMapping("/professors/new")
    public String getJoinProfessor(@ModelAttribute("professorForm") ProfessorForm professorForm, Model model) {
        return "/member/createProfessorForm";
    }

    @PostMapping("/professors/new")
    public String postJoinProfessor(ProfessorForm form) {
        ProfessorInfoDto professorInfoDto = new ProfessorInfoDto(null, form.getName(), form.getPassword(), form.getMajor(), form.getEmail(), form.getOffice());
        professorService.join(professorInfoDto);
        return "redirect:/";
    }

    @GetMapping("/professors")
    public String getProfessorList(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
        List<ProfessorInfoDto> professors = professorService.findProfessors(searchForm.getProfessorName(), searchForm.getMajor());
        model.addAttribute("professors", professors);
        return "/member/professorList";
    }
}
