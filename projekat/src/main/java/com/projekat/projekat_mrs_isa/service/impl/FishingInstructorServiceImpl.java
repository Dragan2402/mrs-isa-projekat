package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.repository.FishingInstructorRepository;
import com.projekat.projekat_mrs_isa.service.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {

    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;

    @Override
    public FishingInstructor findById(Long id) {
        return fishingInstructorRepository.findById(id).orElse(null);
    }

    @Override
    public List<FishingInstructor> findAll() {
        return fishingInstructorRepository.findAll();
    }

    @Override
    public FishingInstructor save(FishingInstructor fishingInstructor) {
        return fishingInstructorRepository.save(fishingInstructor);
    }

    @Override
    public void remove(Long id) {
        fishingInstructorRepository.deleteById(id);
    }

    @Override
    public FishingInstructor findByEmail(String email) {
        return fishingInstructorRepository.findByEmail(email);
    }
}
