package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

}
