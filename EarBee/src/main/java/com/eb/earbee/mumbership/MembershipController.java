package com.eb.earbee.mumbership;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/earbee")
public class MembershipController {

    @GetMapping("/mypage")
    public String mypage(){
        return "mumbership/mumbership";
    }
}
