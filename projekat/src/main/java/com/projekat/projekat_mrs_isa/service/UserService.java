package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.dto.PasswordResetDTO;
import com.projekat.projekat_mrs_isa.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    User findById(long id);
    User save(User user);
    UserDetails loadUserByUsername(String username);
    Boolean isUsernameAvailable(String username);
    Boolean isMailAvailable(String mail);
    User findByUsername(String name);

    Boolean sendResetPassword(String mail);

    Boolean resetPassword(PasswordResetDTO passwordResetDTO);
}
