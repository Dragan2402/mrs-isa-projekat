package com.projekat.projekat_mrs_isa.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderComponent {

    private PasswordEncoder passwordEncoder;

    public PasswordEncoder getPasswordEncoder(){
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }

    public String encode(String rawPassword){
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder.encode(rawPassword);
    }

    public Boolean decode(String rawPassword,String encodedPassword){
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }
}
