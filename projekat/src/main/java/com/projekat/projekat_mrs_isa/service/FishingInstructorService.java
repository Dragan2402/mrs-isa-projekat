package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.repository.FishingInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishingInstructorService {
    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;

    public FishingInstructor findById(Long id) {
        return fishingInstructorRepository.findById(id).orElse(null);
    }

    public List<FishingInstructor> findAll() {
        return fishingInstructorRepository.findAll();
    }

    public Page<FishingInstructor> findAll(Pageable page) {
        return fishingInstructorRepository.findAll(page);
    }

    public FishingInstructor save(FishingInstructor fishingInstructor) {
        return fishingInstructorRepository.save(fishingInstructor);
    }

    public void remove(Long id) {
        fishingInstructorRepository.deleteById(id);
    }

    public FishingInstructor findByEmail(String email) {
        return fishingInstructorRepository.findByEmail(email);
    }
}
