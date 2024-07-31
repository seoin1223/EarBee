package com.eb.earbee.main.api;


import com.eb.earbee.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
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
}
