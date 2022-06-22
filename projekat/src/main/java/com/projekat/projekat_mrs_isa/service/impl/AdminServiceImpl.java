package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.PasswordChangeDTO;
import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.repository.AdminRepository;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin findByUsername(String name) {
        return adminRepository.findByUsername(name);

    }

    @Override
    public Boolean updatePassword(Admin loggedAdmin, PasswordChangeDTO passwordChangeDTO) {
        loggedAdmin.setPassword(passwordEncoderComponent.encode(passwordChangeDTO.getNewPassword()));
        loggedAdmin.setFirstLogin(false);
        save(loggedAdmin);
        return true;
    }

    @Override
    public List<ReservationDTO> getPastReservations() {
        List<ReservationDTO> reservationsDTOS = new ArrayList<>();
        for (ReservationDTO reservationDTO : reservationRepository.findAllDTO()) {
            if (reservationDTO.getStart().plus(Duration.ofMillis(reservationDTO.getDuration())).compareTo(LocalDateTime.now()) < 0) reservationsDTOS.add(reservationDTO);
        }
        return reservationsDTOS;
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

    @Override
    public Admin addAdmin(Map<String, Object> adminMap) {
        Admin admin = new Admin(
                (String) adminMap.get("email"),
                (String) adminMap.get("username"),
                passwordEncoderComponent.encode((String) adminMap.get("password")),
                "pictures/user_pictures/0.png",
                (String) adminMap.get("firstName"),
                (String) adminMap.get("lastName"),
                (String) adminMap.get("address"),
                (String) adminMap.get("city"),
                (String) adminMap.get("country"),
                (String) adminMap.get("phoneNum")
        );
        List<Role> adminRoles = roleRepository.findByName("ROLE_ADMIN");
        admin.setRoles(adminRoles);
        adminRepository.save(admin);
        return admin;
    }
}
