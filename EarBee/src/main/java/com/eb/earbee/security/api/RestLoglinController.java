package com.eb.earbee.security.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestLoglinController {
    @PostMapping("/login")
    public ResponseEntity<String> restLogin() {
        return ResponseEntity.ok("Login Successful");
    }
}
