package com.eb.earbee.security.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tempuser")
@Data
public class TemporaryUser {

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

    @Builder
    public TemporaryUser(String id, String password,String username, String email, String provider, String providerId, String role) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }


}
