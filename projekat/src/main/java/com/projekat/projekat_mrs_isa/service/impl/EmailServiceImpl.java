package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.Review;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private Environment environment;

    @Async
    @Override
    public void confirmReservationMail(Client client, Reservation reservation){
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(client.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Reservation confirmation");
        mail.setText(mailGeneratorOffer(client.getFirstName()+" "+client.getLastName(),reservation.getRentingEntity().getName(),reservation.getStart()));
        javaMailSender.send(mail);
    }

    private String mailGeneratorOffer(String clientName, String entityName, LocalDateTime date){
        String text="Dear,\n"+clientName+"\n\n";
        text += "You have successfully made a reservation for "+entityName+" for the date ";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        text += dateTimeFormatter.format(date)+". Enjoy your stay!\n\n\n";
        text += "Thank you for using Renting Buddy service";
        return text;

    }

    private String verificationMailGenerator(String clientName,Long id){
        id=id*41+105;
        String text="Dear,\n"+clientName+"\n\n";
        text += "You have successfully created a profile on RentingBuddy. Your account is connected with this mail, but still not verified. You will not be able " +
                "to login until you verify your account. To verify your account, please follow this link: \n" +
                "https://renting-buddy-front.herokuapp.com/verification/aTvHtI"+id.toString()+" \n\n\nThank you for using Renting Buddy service";
        return text;
    }
    @Async
    @Override
    public void sendVerificationMail(UserDTO userDTO) {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(userDTO.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Profile Verification");
        mail.setText(verificationMailGenerator(userDTO.getFirstName()+" "+userDTO.getLastName(), userDTO.getId()));
        javaMailSender.send(mail);
    }

    @Override
    @Async
    public void sendResetPasswordMail(String mailP, String token) {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(mailP);
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Password Reset");
        mail.setText(resetPasswordMailGenerator(token));
        javaMailSender.send(mail);
    }

    @Override
    @Async
    public void sendReview(User client, User owner, Review review) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(owner.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Review approved");
        String text="Dear,\n"+owner.getFirstName() + " " + owner.getLastName() +"\n\n";
        text += client.getFirstName() + " " + client.getLastName() + " rated your property.";
        mail.setText(text);
        javaMailSender.send(mail);
    }

    private String resetPasswordMailGenerator(String token) {
        return "You have successfully created a password reset request.\nTo complete your password reset please follow the provided link:\n" +
                "https://renting-buddy-front.herokuapp.com/newPassword/"+token+"\n\n\nThank you for using Renting Buddy service";
    }
}
