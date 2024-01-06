package com.eb.earbee.signin.service;

import com.eb.earbee.signin.entiry.SignIn;
import com.eb.earbee.signin.repository.MemorySignInRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SignInServiceTest {

    SignInService signInService;
    MemorySignInRepository signInRepository;

    @BeforeEach
    public void beforeEach(){
        signInRepository = new MemorySignInRepository();
        signInService = new SignInService(signInRepository);
    }

    // 저장소 비워줌
    @AfterEach
    public void afterEach(){
        signInRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // 실제
        SignIn signin = new SignIn();
        signin.setName("치지직");

        // 예상
        Long saveId = signInService.join(signin);

        // 비교
        SignIn findsignin = signInService.findOne(saveId).get();
        assertThat(signin.getName()).isEqualTo(findsignin.getName());

    }

    @Test
    public void 중복_회원_예외(){
        // 실제
        SignIn signin1 = new SignIn();
        signin1.setName("숲");

        SignIn signin2 = new SignIn();
        signin2.setName("숲");

        // 예상
        signInService.join(signin1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> signInService.join(signin2));

        // 비교
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다!");

    }


}