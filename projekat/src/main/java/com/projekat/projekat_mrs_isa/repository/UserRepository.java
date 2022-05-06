package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
