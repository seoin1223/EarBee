package com.eb.earbee.signin.service;

import com.eb.earbee.signin.entity.SignInEntity;
import com.eb.earbee.signin.repository.SignInRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SignInService {


    @Autowired
    SignInRepository signInRepository;

    // 유저 기본키 중복 체크 함수
    public boolean checkUserNum(SignInEntity signin) {
        Optional<SignInEntity> byId = signInRepository.findById(signin.getNum()); // 기본키 중복 체크
        SignInEntity existingEntity = byId.orElse(null); // null 체크
        return existingEntity != null;
    }

    // 회원가입 함수
    private SignInEntity addUser(SignInEntity signin) { // 유저 정보를 repository에 추가하는 메서드
        boolean check = checkUserNum(signin); // 유저 중복 체크
        if(!check){
            return null;
        }
        return signInRepository.save(signin);
    }


    public  Optional<SignInEntity> findOne(Long signInId){
        return  signInRepository.findById(signInId);
    }

}
