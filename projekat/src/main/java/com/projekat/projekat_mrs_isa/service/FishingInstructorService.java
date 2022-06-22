package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.FishingInstructor;

import java.util.List;
import java.util.Map;



public interface FishingInstructorService {


    FishingInstructor findById(Long id);

    List<FishingInstructor> findAll();

    FishingInstructor save(FishingInstructor fishingInstructor);

    void remove(Long id);

    FishingInstructor findByEmail(String email);

    FishingInstructor addFishingInstructor(Map<String, Object> userMap);

    List<UserDTO> findAllDTO();

    FishingInstructor findByUsername(String username);

    List<ReservationDTO> getReservationsFromInstructor(FishingInstructor instructor);
}
