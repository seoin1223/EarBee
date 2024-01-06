package com.eb.earbee.repository;

import com.eb.earbee.signin.entiry.SignIn;
import com.eb.earbee.signin.repository.MemorySignInRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemorySignInRepositoryTest {

    MemorySignInRepository repository = new MemorySignInRepository();

    // 저장소 비워줌
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        // 실제
        SignIn signIn = new SignIn();
        signIn.setName("윤원용");

        // 예상
        repository.save(signIn);

        SignIn result = repository.findById(signIn.getId()).get();

        // 비교
        assertThat(signIn).isEqualTo(result);
    }

    @Test
    public void findByName(){
        // 실제
        SignIn signin1 = new SignIn();
        signin1.setName("워뇽띠");
        repository.save(signin1);

        SignIn signin2 = new SignIn();
        signin2.setName("최두환");
        repository.save(signin2);

        // 예상
        SignIn result = repository.findByName("워뇽띠").get();

        //비교
        assertThat(result).isEqualTo(signin1);
        System.out.println(signin1);
    }

    @Test
    public void findAll(){
        //실제
        SignIn signin1 = new SignIn();
        signin1.setName("워뇽띠");
        repository.save(signin1);

        SignIn signin2 = new SignIn();
        signin2.setName("최두환");
        repository.save(signin2);

        // 예상
        List<SignIn> result = repository.findAll();

        // 비교
        assertThat(result.size()).isEqualTo(2);
    }
}
