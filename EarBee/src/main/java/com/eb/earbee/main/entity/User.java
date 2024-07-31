package com.eb.earbee.main.entity;


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
    private String providerId;
    @Column
    private String phone;
    @CreationTimestamp
    private Timestamp created;

    @Builder
    public User(String id, String password, String username, String email, String phone,String provider, String providerId, String role) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }


}
