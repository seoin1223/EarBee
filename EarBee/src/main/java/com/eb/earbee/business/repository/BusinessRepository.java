package com.eb.earbee.business.repository;

import com.eb.earbee.business.entity.Business;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface BusinessRepository extends CrudRepository<Business, Long> {

    @Override
    ArrayList<Business> findAll();
}
