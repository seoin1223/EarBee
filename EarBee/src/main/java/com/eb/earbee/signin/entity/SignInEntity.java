package com.eb.earbee.signin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num; // 회원 번호

    @Column(nullable = false, length = 12, unique = true)
    private String id; // 찐 아이디

    @Column(nullable = false, length = 20)
    private String pwd;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean rrn;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String addr;

//    @Column
//    private String role;

}
