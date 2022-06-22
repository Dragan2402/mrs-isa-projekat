package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.PasswordChangeDTO;
import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.model.Admin;

import java.util.List;
import java.util.Map;


public interface AdminService {

    Admin findById(Long id);

    List<Admin> findAll();

    List<ReservationDTO> getPastReservations();

    Admin findByUsername(String name);

    Boolean updatePassword(Admin loggedAdmin, PasswordChangeDTO passwordChangeDTO);

    Admin save(Admin admin);

    void remove(Long id);

    Admin findByEmail(String email);

    Admin addAdmin(Map<String, Object> adminMap);
}
