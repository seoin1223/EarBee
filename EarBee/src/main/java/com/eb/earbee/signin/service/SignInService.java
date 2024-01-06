package com.eb.earbee.signin.service;

import com.eb.earbee.signin.entiry.SignIn;
import com.eb.earbee.signin.repository.MemorySignInRepository;
import com.eb.earbee.signin.repository.SignInRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SignInService {



    private final SignInRepository signInRepository;

    // 리파지터리를 외부에서 넣을수있게
    @Autowired
    public SignInService(SignInRepository signInRepository) {
        this.signInRepository = signInRepository;
    }

    // 회원가입
    public Long join(SignIn signin) {
        duplicationSignIn(signin); // 중복 검증
        signInRepository.save(signin);
        return signin.getId();
    }

    private void duplicationSignIn(SignIn signin) {
        signInRepository.findByName(signin.getName())
            .ifPresent(s ->  {
                throw new IllegalStateException("이미 존재하는 회원입니다!");
            });
    }

    // 회원 조회
    public List<SignIn> findSignIn(){
        return signInRepository.findAll();
    }

    public  Optional<SignIn> findOne(Long signInId){
        return  signInRepository.findById(signInId);
    }

}
