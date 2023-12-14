package com.eb.earbee.business.service;

import com.eb.earbee.business.entity.Business;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface BusinessService extends CrudRepository<Business, Long> {

    @Override
    ArrayList<Business> findAll();
}
