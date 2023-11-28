package com.eb.earbee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 생성
@Builder // 1. 생성자가 없는 경우 : 모든 멤버 변수를 파라미터로 받는 기본 생성자 생성
         // 2. 생성자가 있는 경우 : 따로 생성자를 생성하지 않음
public class User {
    private int id;
    private String userName;
    private String passwd;

}
