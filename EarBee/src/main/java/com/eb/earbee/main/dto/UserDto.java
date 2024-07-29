package com.eb.earbee.main.dto;

import com.eb.earbee.main.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String email;
    private String provider;
    private String providerId;
    private String role;


    public User toEntity(){
        return new User(id,username,password,email,provider,providerId,role);
    }
}
