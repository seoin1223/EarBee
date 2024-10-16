package com.eb.earbee.business.entity;

import com.eb.earbee.main.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column
    private String title;

    @Column(name = "b_no")
    private String bNo;

    @Column(name = "zip_code")
    private String zipCode;

    @Column
    private String addr;

    @Column
    private String detail;

    @Column
    private String author;

    @Column
    private String checked;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_num", referencedColumnName = "num", nullable = false)
    private User user;

    public Business(Long id, String title, String bNo, String zipCode, String addr, String detail,String checked) {
        this.title = title;
        this.bNo = bNo;
        this.zipCode = zipCode;
        this.addr = addr;
        this.detail = detail;
        this.checked = checked;

    }


}
