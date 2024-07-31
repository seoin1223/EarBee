package com.eb.earbee.security.batch;

import com.eb.earbee.security.repository.TemporaryUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TemporaryUserCleanService {

    @Autowired
    private TemporaryUserRespository temporaryUserRespository;

    @Scheduled(cron ="0 0 0 * * ?")
    public void cleanTemporaryUsers() {
        temporaryUserRespository.deleteAll();
        System.out.println("Clean temporary users");
    }
}
