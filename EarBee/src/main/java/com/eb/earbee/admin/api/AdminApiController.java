package com.eb.earbee.admin.api;


import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    @Autowired
    private UserRepository repository;


    @GetMapping("/user/search")
    public ResponseEntity<List<User>> search() {
        List<User> users = repository.findAllUsers();
        if (!users.isEmpty()) {
            System.out.println(users);
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.noContent().build(); // 204 No Content

    }



}
