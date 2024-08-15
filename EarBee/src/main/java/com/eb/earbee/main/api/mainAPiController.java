package com.eb.earbee.main.api;


import com.eb.earbee.main.dto.UserDto;
import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.repository.UserRepository;
import com.eb.earbee.main.service.UserService;
import com.eb.earbee.security.login.basicLogin.PrincipalUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class mainAPiController {

    private final UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    public mainAPiController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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
    public ResponseEntity<Map<String, Object>> checkLogin(Authentication authed){
        Map<String, Object> response = new HashMap<>();
        if(authed != null ){
            if(authed.getPrincipal() != null){
                PrincipalUserDetails userPrincipal = (PrincipalUserDetails ) authed.getPrincipal();
                response.put("ok", true);
                response.put("role", userPrincipal.getUser().getRole());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/checkUserMyPage")
    public ResponseEntity<String> checkUserMyPage(UserDto check){
        User user = userRepository.findById(check.getId());
        boolean match = passwordEncoder.matches(check.getPassword(),user.getPassword());
        if(match){
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/check_alias")
    public ResponseEntity<String> checkUsername(@RequestBody Map<String, String> aliass){
        if (!aliass.containsKey("alias")) {
            return ResponseEntity.badRequest().body("alias가 사라짐.");
        }
        String alias = aliass.get("alias");
        boolean check = userService.isUserAlias(alias);
        return (check)? ResponseEntity.ok("ok") : ResponseEntity.badRequest().build();
    }



}
