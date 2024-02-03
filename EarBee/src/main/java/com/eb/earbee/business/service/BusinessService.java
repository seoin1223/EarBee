package com.eb.earbee.business.service;

import com.eb.earbee.business.entity.Business;
import com.eb.earbee.business.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessService  {

    @Autowired
    BusinessRepository businessRepository;

    // 비즈니스 게시판 모든 게시글 조회
    public List<Business> findAll() {
        return null;
    }

    @Transactional
    public Business addBusiness(Business entity) {
        Business business = businessRepository.save(entity);
        return business;
    }
}
