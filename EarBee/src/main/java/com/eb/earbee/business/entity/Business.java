package com.eb.earbee.business.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "b_no")
    private String bNo;

    @Column(name = "zip_code")
    private String zipCode;

    @Column
    private String addr;

    @Column
    private String detail;
}
