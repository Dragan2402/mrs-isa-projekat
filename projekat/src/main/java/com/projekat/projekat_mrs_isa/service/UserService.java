package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){return userRepository.findAll();}
}
