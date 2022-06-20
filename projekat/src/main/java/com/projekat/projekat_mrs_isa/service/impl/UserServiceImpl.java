package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.PasswordChangeDTO;
import com.projekat.projekat_mrs_isa.dto.PasswordResetDTO;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.repository.UserRepository;
import com.projekat.projekat_mrs_isa.service.EmailService;
import com.projekat.projekat_mrs_isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }

    @Override
    public Boolean isUsernameAvailable(String username) {

        List<User> users= userRepository.findAll();
        for(User user : users){
                if(user.getUsername().equals(username)){
                    return false;
                }
        }
        return true;

    }

    @Override
    public Boolean isMailAvailable(String mail) {
        List<User> users= userRepository.findAll();
        for(User user : users){
            if(user.getEmail().toLowerCase().equals(mail))
                return false;
        }
        return true;
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);

    }

    @Override
    public Boolean sendResetPassword(String mail) {
        User user = userRepository.findByEmail(mail);
        if(user==null)
            return false;
        user.setPasswordResetToken(generateToken());
        user.setPasswordResetTokenDate(Timestamp.valueOf(LocalDateTime.now()));
        save(user);
        emailService.sendResetPasswordMail(mail,user.getPasswordResetToken());
        return true;
    }

    @Override
    public Boolean resetPassword(PasswordResetDTO passwordResetDTO) {
        User user= userRepository.findByToken(passwordResetDTO.getToken());
        if (user==null)
            return false;
        if(isTokenExpired(user.getPasswordResetTokenDate()))
            return false;
        user.setPassword(passwordEncoderComponent.encode(passwordResetDTO.getNewPassword()));
        user.setPasswordResetTokenDate(null);
        user.setPasswordResetToken(null);
        save(user);
        return true;

    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();
        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    public boolean isTokenExpired(Timestamp tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate.toLocalDateTime(), now);
        System.out.println(diff);
        return diff.toMinutes() >= 60;
    }

    @Override
    public Boolean updatePassword(User logged, PasswordChangeDTO passwordChangeDTO) {
        if(!passwordEncoderComponent.decode(passwordChangeDTO.getOldPassword(), logged.getPassword()))
            return false;
        logged.setPassword(passwordEncoderComponent.encode(passwordChangeDTO.getNewPassword()));
        save(logged);
        return true;
    }

}
