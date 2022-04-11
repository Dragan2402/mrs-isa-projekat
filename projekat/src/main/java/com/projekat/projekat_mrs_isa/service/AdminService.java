package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Page<Admin> findAll(Pageable page) {
        return adminRepository.findAll(page);
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public void remove(Long id) {
        adminRepository.deleteById(id);
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}
