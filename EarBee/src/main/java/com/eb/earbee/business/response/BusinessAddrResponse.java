package com.eb.earbee.business.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessAddrResponse {
    int id; // 순번
    String zipNo; // 우편번호
    String siNm; // 시도명
    String roadAddr; // 전체 도로명 주소
    int currentPage; // 현재 페이지
    int totalCount; // 검색한 총 주소 개수


}
