package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import java.util.List;


public interface FishingInstructorService {

    FishingInstructor findById(Long id);

    List<FishingInstructor> findAll();

    FishingInstructor save(FishingInstructor fishingInstructor);

    void remove(Long id);

    FishingInstructor findByEmail(String email);
}
