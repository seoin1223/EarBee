package com.eb.earbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class  EarBeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EarBeeApplication.class, args);

    }



}
