package com.eb.earbee.main.api;


import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.service.UserService;
import com.eb.earbee.security.login.basicLogin.PrincipalUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class mainAPiController {

    private UserService userService;

    public mainAPiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/check_id")
    public ResponseEntity<String> checkId(@RequestBody Map<String, String> body){
        if (!body.containsKey("id")) {
            return ResponseEntity.badRequest().body("ID가 사라짐.");
        }
        String id = body.get("id");
        return (!userService.isUser(id))? ResponseEntity.ok("ok") : ResponseEntity.badRequest().build();
    }

    @GetMapping("/check_login")
    public ResponseEntity<String> checkLogin(Authentication authen){
        if(authen != null ){
            if(authen.getPrincipal() != null){
                PrincipalUserDetails userPrincipal = (PrincipalUserDetails ) authen.getPrincipal();
                return ResponseEntity.ok(userPrincipal.getUser().getRole());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
