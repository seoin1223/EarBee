package com.eb.earbee.admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/management")
    public String UserManagement(@RequestParam("check")String check, Model m) {
        System.out.println("check"+check);
        m.addAttribute("check", check);
        return "admin/Admin";

    }
}
