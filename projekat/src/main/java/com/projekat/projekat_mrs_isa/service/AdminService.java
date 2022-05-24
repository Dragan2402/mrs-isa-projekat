package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Admin;
import java.util.List;


public interface AdminService {

    Admin findById(Long id);

    List<Admin> findAll();

    Admin save(Admin admin);

    void remove(Long id);

    Admin findByEmail(String email);
}
