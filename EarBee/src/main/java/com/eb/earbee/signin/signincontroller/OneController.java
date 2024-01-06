package com.eb.earbee.signin.signincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OneController {

    @GetMapping("/one")
    public String one(){
        return "signin/one";
    }
}
