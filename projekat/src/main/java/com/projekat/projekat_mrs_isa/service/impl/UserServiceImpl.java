package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.repository.UserRepository;
import com.projekat.projekat_mrs_isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;




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

}
