package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.model.Complaint;

import java.util.List;


public interface AdminService {

    Admin findById(Long id);

    List<Admin> findAll();
    List<ComplaintDTO> findAllComplaintsDTO();

    Complaint findComplaintById(Long id);

    Admin save(Admin admin);

    void remove(Long id);

    Admin findByEmail(String email);
}
