package com.eb.earbee.security.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long num;
    private String id;
    private String username;
    private String provider;
    private String providerId;
    private String phone;
    private Timestamp created;
    private String alias;
    private String password;
    private String role;
}
