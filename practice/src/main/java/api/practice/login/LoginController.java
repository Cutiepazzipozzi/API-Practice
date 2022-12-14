package api.practice.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginRepository loginRepository;

    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult b, HttpServletRequest request) {
        if(b.hasErrors()) {
            return "login/loginForm";
        }

        Member logMember = loginRepository.login(form.getId(), form.getPw());

        if(logMember == null) {
            b.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다");
            return "login/loginForm";
        }

        //로그인을 성공했다면
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, logMember);

        return "redirect:/";
    }

    public class SessionConst {
        public static final String LOGIN_MEMBER = "logMember";
    }

//    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult b, HttpServletResponse response) {
//        if(b.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member logMember = loginRepository.login(form.getId(), form.getPw());
//
//        if(logMember == null) {
//            b.reject("loginFail", "아이디 혹은 비밀번호가 잘못됐습니다!");
//            return "login/loginForm";
//        }
//
//        sessionManager.createSession(logMember, response);
//        return "redirect:/";
//    }

//    public String login(@Valid @ModelAttribute LoginForm form, BindingResult b, HttpServletResponse response) {
//        //bindingResult는 검증 오류가 발생할 경우 오류 내용을 보관
//        if(b.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member logMember = loginRepository.login(form.getId(), form.getPw());
//
//        if(logMember == null) {
//            b.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다");
//            return "login/loginForm";
//        }
//
//        Cookie idCookie = new Cookie("mId", String.valueOf(logMember.getId()));
//        response.addCookie(idCookie);
//
//        return "redirect:/";
//     }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
            //invalidate를 해주면 세션이 삭제됨
        }
        return "redirect:/";
    }
//    public String logout(HttpServletResponse response) {
//        expireCookie(response, "mId");
//        return "redirect:/";
//     }

    private void expireCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
     }
}
