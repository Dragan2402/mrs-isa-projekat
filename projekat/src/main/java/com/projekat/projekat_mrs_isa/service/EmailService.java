package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;


    @Async
    public void testMail(){
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo("draganmirkovic2402@gmail.com");
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Reservation confirmation");
        mail.setText("You have successfully made a reservation for THE THING");
        javaMailSender.send(mail);
    }


    @Async
    public void confirmReservationMail(Client client, Reservation reservation){
        SimpleMailMessage mail=new SimpleMailMessage();
        System.out.println("USAO SI OVDJE IDE POZIV");
        System.out.println(client.getEmail());
        mail.setTo(client.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Reservation confirmation");
        mail.setText(mailGenerator(client.getFirstName()+" "+client.getLastName(),reservation.getRentingEntity().getName(),reservation.getStart()));
        javaMailSender.send(mail);
    }


    public String mailGenerator(String clientName, String entityName, LocalDateTime date){
        String text="Dear,\n"+clientName+"\n\n";
        text += "You have successfully made a reservation for "+entityName+" for the date ";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        text += dateTimeFormatter.format(date)+". Enjoy your stay!\n\n\n";
        text += "Thank you for using Renting Buddy service";
        return text;

    }
}
