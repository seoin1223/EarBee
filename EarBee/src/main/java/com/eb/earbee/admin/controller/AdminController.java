package com.eb.earbee.admin.controller;


import com.eb.earbee.security.login.basicLogin.PrincipalUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/management")
    public String UserManagement(@RequestParam("check")String check, Model model, Authentication authen) throws JsonProcessingException {
        if(authen != null){
            PrincipalUserDetails principal = (PrincipalUserDetails) authen.getPrincipal();
            if(principal != null){
                ObjectMapper mapper = new ObjectMapper();
                String role = principal.getUser().getRole();
                String user = mapper.writeValueAsString(principal.getUser().toFilerUser());
                if(role.equals("ROLE_ADMIN")) {
                    model.addAttribute("role", role);
                    model.addAttribute("check", check);
                    model.addAttribute("id", principal.getId());
                    model.addAttribute("user",user);
                    return "admin/Admin";
                }
            }
        }
        return "/";
    }
}
