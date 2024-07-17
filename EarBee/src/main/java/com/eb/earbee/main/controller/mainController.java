package com.eb.earbee.main.controller;



import com.eb.earbee.main.dto.UserDto;
import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.service.UserService;
import com.eb.earbee.security.oauth.PrincipalUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {

    private UserService userService;

    public mainController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public String index( Authentication authentication, Model m){

        if(authentication != null && authentication.isAuthenticated()) {
            PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authentication.getPrincipal();
            User user = principalUserDetails.getUser();

            if (user != null) {
                m.addAttribute("id", user.getId());
                System.out.println(user.toString());
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

    @GetMapping("/login")
    public String login(Model model){
        return "user/login";
    }

    @PostMapping("/join")
    public String join(UserDto user){
        System.out.println(user.toString());
        User resultUser = userService.addUser(user);
        return "redirect:/";

    }


}
