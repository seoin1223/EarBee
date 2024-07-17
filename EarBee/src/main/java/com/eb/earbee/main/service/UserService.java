package com.eb.earbee.main.service;

import com.eb.earbee.main.dto.UserDto;
import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    public UserService(UserRepository userRepository,BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public User addUser(UserDto user) {
        User userEntity = user.toEntity();
        userEntity.setRole("ROLE_USER");
        userEntity.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(userEntity);


    }

}
