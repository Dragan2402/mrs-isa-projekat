package com.projekat.projekat_mrs_isa.repository;



import com.projekat.projekat_mrs_isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Query("select u from User u where u.email= ?1")
    User findByEmail(String email);

    @Query("select u from User u where u.passwordResetToken=?1")
    User findByToken(String token);
}
