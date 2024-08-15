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
        return user != null;
    }

    public User addUser(UserDto user, boolean check) {
        User userEntity = user.toEntity();
        if (check) {
            userEntity.setPassword(encoder.encode(user.getId())); // Assuming ID is used for password encoding
        } else {
            userEntity.setPassword(encoder.encode(user.getPassword()));
        }
        userEntity.setRole("ROLE_USER");
        return userRepository.save(userEntity);
    }

    public boolean isUserAlias(String alias){
        User user = userRepository.findByAlias(alias);
        return user == null;
    }

    public User updateUser(User user) {
        User postUser = userRepository.findById(user.getId());
        postUser.setAlias(user.getAlias());
        postUser.setPhone(user.getPhone());
        return userRepository.save(postUser);
    }

}
