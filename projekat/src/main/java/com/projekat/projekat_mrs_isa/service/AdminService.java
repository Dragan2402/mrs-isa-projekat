package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.PasswordChangeDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Complaint;

import java.util.List;


public interface AdminService {

    Admin findById(Long id);

    List<Admin> findAll();

    Admin findByUsername(String name);

    Boolean updatePassword(Admin loggedAdmin, PasswordChangeDTO passwordChangeDTO);

    Admin save(Admin admin);

    void remove(Long id);

    Admin findByEmail(String email);
}
