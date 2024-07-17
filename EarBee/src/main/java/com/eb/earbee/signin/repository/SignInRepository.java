package com.eb.earbee.signin.repository;

import com.eb.earbee.signin.entity.SignInEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignInRepository extends CrudRepository<SignInEntity, Long> {



}
