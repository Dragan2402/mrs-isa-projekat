package com.projekat.projekat_mrs_isa.util;


import com.projekat.projekat_mrs_isa.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@EnableScheduling
public class Scheduler {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Autowired
    private ClientService clientService;

    @Scheduled(cron = "@monthly")
    public void penaltiesReset() {
        clientService.resetPenalties();
        System.out.println("Resetting penalties :::::> Execution Time - "+dateTimeFormatter.format(LocalDateTime.now()) );
    }


}
