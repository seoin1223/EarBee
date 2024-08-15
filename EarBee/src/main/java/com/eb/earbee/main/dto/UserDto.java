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
    private String phone1;
    private String phone2;
    private String provider;
    private String providerId;
    private String role;
    private String alias;


    public User toEntity(){
        return User.builder().id(id).username(username).password(password).email(email).phone("010"+phone1+phone2).provider(provider).providerId(providerId).role(role).alias(alias).build();
    }
}
