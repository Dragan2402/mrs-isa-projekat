package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    @Query("select a from Admin a where a.email= ?1")
    public Admin findByEmail(String email);
}
