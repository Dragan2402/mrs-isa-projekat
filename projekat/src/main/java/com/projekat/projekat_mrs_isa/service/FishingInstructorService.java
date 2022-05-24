package com.projekat.projekat_mrs_isa.service;


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

}
