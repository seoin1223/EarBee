package com.eb.earbee.main.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String role;
    @Column
    private String provider;
    @Column
    private String providerId;
    @CreationTimestamp
    private Timestamp created;


    public User(String id, String password,String username, String email, String provider, String providerId) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }




}