package com.eb.earbee.main.controller;



import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails user, Model m){
        if (user != null) {
            m.addAttribute("userName", user.getUsername());
            System.out.println(user.getUsername()); // 사용자 이름 출력
        } else {
            System.out.println("User is not logged in.");
        }
        return "main/main"; // Mustache 템플릿 이름
    }


}
