package com.eb.earbee.main.service;

import com.eb.earbee.main.dto.UserDto;
import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    public UserService(UserRepository userRepository,BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public boolean isUser(String id){
        User user = userRepository.findById(id);
        System.out.println("id: "+id+"\n user: "+user);
        return user != null;
    }

    public User addUser(UserDto user, boolean check) {
        User userEntity = user.toEntity();
        if (check) {
            System.out.println("Before encoding: " + userEntity);
            userEntity.setPassword(encoder.encode(user.getId())); // Assuming ID is used for password encoding
            System.out.println("Before encoding2: " + userEntity);
        } else {
            System.out.println("Before encoding: " + userEntity);
            userEntity.setPassword(encoder.encode(user.getPassword()));
        }
        userEntity.setRole("ROLE_USER");
        return userRepository.save(userEntity);
    }

    public boolean isUserAlias(String alias){
        System.out.println("alias: " + alias);
        User user = userRepository.findByAlias(alias);
        System.out.println("alias User check: " +user);
        return user == null;
    }

    public User updateUser(User user) {
        User postUser = userRepository.findById(user.getId());
        postUser.setAlias(user.getAlias());
        postUser.setPhone(user.getPhone());
        return userRepository.save(postUser);
    }

}
