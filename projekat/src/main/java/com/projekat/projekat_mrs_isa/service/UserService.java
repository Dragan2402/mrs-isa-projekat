package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> findAll();
    public User findById(long id);
    public User save(User user);
    public UserDetails loadUserByUsername(String username);

    User findByUsername(String name);
}
