package com.eb.earbee.main.controller;



import com.eb.earbee.main.dto.UserDto;
import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.service.UserService;
import com.eb.earbee.security.entity.TemporaryUser;
import com.eb.earbee.security.login.basicLogin.PrincipalUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserService userService;

    public mainController(UserService userService, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @GetMapping
    public String index(Authentication authentication, Model m){

        if(authentication != null && authentication.isAuthenticated()) {

            PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authentication.getPrincipal();
            User user = principalUserDetails.getUser();

            if (user != null) {
                m.addAttribute("id", user.getId());
            }
        }else {
            System.out.println("User is not logged in.");
        }

        return "main/main"; // Mustache 템플릿 이름
    }

    @GetMapping("/join")
    public String joinForm(Model model){
        return "user/joinForm";
    }

    @GetMapping("/joinOauth2")
    public String joinOauth2(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);

        if(session != null) {
            PrincipalUserDetails principal = (PrincipalUserDetails) session.getAttribute("principal");
            if(principal != null) {
                model.addAttribute("user", principal.getTemporaryUser());
                return "/user/joinOauth2Form";
            }
        }
        return "/main/main";
    }

//    @GetMapping("/login")
//    public String login(Model model){
//        return "user/login";
//    }

    @PostMapping("/join")
    public String join(HttpServletRequest request, UserDto user){
        boolean check = false;
        HttpSession session = request.getSession();
        PrincipalUserDetails principal = (PrincipalUserDetails) session.getAttribute("principal");

        if(principal != null) {
            check = principal.isTemporaryUser();
            if(check) {
                session.removeAttribute("principal");
            }
        }
        User resultUser = userService.addUser(user,check);

        return "redirect:/";
    }

    // 마이 페이지
    @GetMapping("/myPage")
    public String myPage(Model model, Authentication authen){
        if(authen != null){
            PrincipalUserDetails principal = (PrincipalUserDetails) authen.getPrincipal();
            if(principal != null){
                model.addAttribute("id", principal.getId());
                model.addAttribute("role",principal.getUser().getRole());
            }
        }
        return "user/myPage";

    }

    @GetMapping("/login")
    public String login(Model model){
         return "user/login";
    }

}
