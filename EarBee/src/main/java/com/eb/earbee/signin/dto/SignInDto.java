package com.eb.earbee.signin.dto;

import com.eb.earbee.signin.entity.SignInEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class SignInDto {


    private Long num;
    private String id;
    private String pwd;
    private String name;

    private boolean rrn;
    private String phone;
    private String email;
    private String addr;
//    private String role;

    public SignInEntity toEntity(){
        return new SignInEntity(num,id,pwd,name,rrn,phone,email,addr);
    }


}
