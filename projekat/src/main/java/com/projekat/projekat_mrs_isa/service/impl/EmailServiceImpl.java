package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.*;
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
                "http://localhost:3000/verification/aTvHtI"+id.toString()+" \n\n\nThank you for using Renting Buddy service";
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

    @Override
    @Async
    public void sendNewOfferMail(Client client, RentingEntity rentingEntity, Offer newOffer) {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(client.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("New Offer at "+rentingEntity.getName());
        String text="Dear,\n"+client.getFirstName()+" "+client.getLastName()+"\n\n";
        text += "There is a new offer at RentingBuddy. Renting entity that you are following just created a new amazing offer." +
                "To check the offer jump to our site and enjoy: \n" +
                "http://localhost:3000/ \n\n\nThank you for using Renting Buddy service";
        mail.setText(text);
        javaMailSender.send(mail);
    }

    @Override
    @Async
    public void sendReport(User client, User submitter, Report report) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(submitter.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Penalty approved!");
        String text="Dear,\n"+submitter.getFirstName() + " " + submitter.getLastName() +"\n\n";
        text += "You successfully gave penalty to " + client.getUsername() + ".";
        mail.setText(text);
        javaMailSender.send(mail);

        SimpleMailMessage mail2 = new SimpleMailMessage();
        mail2.setTo(client.getEmail());
        mail2.setFrom(environment.getProperty("spring.mail.username"));
        mail2.setSubject("You've got penalty!");
        String text2="Dear,\n"+client.getFirstName() + " " + client.getLastName() +"\n\n";
        text2 += "You've got penalty from " + submitter.getUsername() + ".";
        mail2.setText(text2);
        javaMailSender.send(mail2);

    }

    @Override
    @Async
    public void sendDeleteAccount(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Delete approved!");
        String text="Dear,\n"+user.getFirstName() + " " + user.getLastName() +"\n\n";
        text += "Your account has been successfully deleted.";
        mail.setText(text);
        javaMailSender.send(mail);
    }

    @Override
    @Async
    public void sendRegistration(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Registration approved!");
        String text="Dear,\n"+user.getFirstName() + " " + user.getLastName() +"\n\n";
        text += "Your registration request is approved.";
        mail.setText(text);
        javaMailSender.send(mail);
    }

    @Override
    @Async
    public void sendRejectRequest(User user, String adminResponse) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Request rejected!");
        String text="Dear,\n"+user.getFirstName() + " " + user.getLastName() +"\n\n";
        text += "Your request is rejected. Reason: " + adminResponse;
        mail.setText(text);
        javaMailSender.send(mail);
    }

    @Override
    @Async
    public void sendSuccessComplaint(User client, User owner, String adminResponse) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(owner.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("New report!");
        String text="Dear,\n"+owner.getFirstName() + " " + owner.getLastName() +"\n\n";
        text += "You have been reported by " + client.getUsername() + ".\n";
        text += "Admin response: " + adminResponse;
        mail.setText(text);
        javaMailSender.send(mail);

        SimpleMailMessage mail2 = new SimpleMailMessage();
        mail2.setTo(client.getEmail());
        mail2.setFrom(environment.getProperty("spring.mail.username"));
        mail2.setSubject("Report accepted!");
        String text2="Dear,\n"+client.getFirstName() + " " + client.getLastName() +"\n\n";
        text2 += "Your report to " + owner.getUsername() + " has been accepted.\n";
        text2 += "Admin response: " + adminResponse;
        mail2.setText(text2);
        javaMailSender.send(mail2);
    }

    @Override
    @Async
    public void sendRejectComplaint(User client, User owner, String adminResponse) {
        SimpleMailMessage mail2 = new SimpleMailMessage();
        mail2.setTo(client.getEmail());
        mail2.setFrom(environment.getProperty("spring.mail.username"));
        mail2.setSubject("Report rejected!");
        String text2="Dear,\n"+client.getFirstName() + " " + client.getLastName() +"\n\n";
        text2 += "Your report to " + owner.getUsername() + " has been rejected.\n";
        text2 += "Reason: " + adminResponse;
        mail2.setText(text2);
        javaMailSender.send(mail2);
    }

    private String resetPasswordMailGenerator(String token) {
        return "You have successfully created a password reset request.\nTo complete your password reset please follow the provided link:\n" +
                "http://localhost:3000/newPassword/"+token+"\n\n\nThank you for using Renting Buddy service";
    }
}
