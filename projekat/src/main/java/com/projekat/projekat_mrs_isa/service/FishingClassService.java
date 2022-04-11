package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.repository.FishingClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FishingClassService {
    @Autowired
    private FishingClassRepository fishingClassRepository;
}
