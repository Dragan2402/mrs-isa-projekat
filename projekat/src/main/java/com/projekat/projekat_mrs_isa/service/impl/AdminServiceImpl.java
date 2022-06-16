package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.model.Complaint;
import com.projekat.projekat_mrs_isa.repository.AdminRepository;
import com.projekat.projekat_mrs_isa.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void remove(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}
