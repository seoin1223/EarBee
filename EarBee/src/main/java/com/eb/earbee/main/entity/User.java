package com.eb.earbee.main.entity;


import com.eb.earbee.business.entity.Business;
import com.eb.earbee.main.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.sql.Timestamp;


@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor

public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column(unique = true, nullable = false)
    private String id;

    @JsonIgnore
    @Column
    private String password;

    @Column(name="username")
    private String username;

    @Column
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = true)
    private String provider;

    @Column(nullable = true)
    @JsonIgnore
    private String providerId;

    @Column
    private String phone;

    @Column(nullable = false, unique = true)
    private String alias;

    @CreationTimestamp
    private Timestamp created;



    @Builder
    public User(String id, String password, String username, String email, String phone,String provider, String providerId, String role, String alias) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.alias = alias;
    }

    public User(Long num, String id, String username, String email, String role, String phone, String provider,String alias) {
        this.num = num;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.provider = provider;
        this.alias = alias;
    }

    public User toFilerUser(){
        return new User(num,id,username,email,role,phone,provider,alias);
    }


}
