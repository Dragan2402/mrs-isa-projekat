package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.repository.FishingInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FishingInstructorService {
    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;
}
