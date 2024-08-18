package com.eb.earbee.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/UserManagement")
    public String UserManagement() {
        return "/admin/UserManagement";

    }
}
