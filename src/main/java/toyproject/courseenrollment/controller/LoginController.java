package toyproject.courseenrollment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.courseenrollment.controller.form.LoginForm;
import toyproject.courseenrollment.exception.LoginException;
import toyproject.courseenrollment.service.LoginService;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/student")
    public String getStudentLogin(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "/loginStudent";
    }

    @PostMapping("/student")
    public String postStudentLogin(LoginForm form, HttpSession session) {
        try {
            Long studentId = loginService.studentLogin(form.getStudentId(), form.getPassword());
            session.setAttribute("studentId", studentId);
            log.info("student login = {}", studentId);
            return "/studentHome";
        } catch (LoginException e) {
            log.error("student login fail: {}", e.getMessage());
            return "redirect:/student";
        }
    }

    @GetMapping("/professor")
    public String getProfessorLogin(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "/loginProfessor";
    }

    @PostMapping("/professor")
    public String postProfessorLogin(LoginForm form, HttpSession session) {
        try {
            Long professorId = loginService.professorLogin(form.getEmail(), form.getPassword());
            log.info("professor login = {}", professorId);
            session.setAttribute("professorId", professorId);
            return "/professorHome";
        } catch (LoginException e) {
            log.error("professor login fail: {}", e.getMessage());
            return "redirect:/professor";
        }
    }
}
