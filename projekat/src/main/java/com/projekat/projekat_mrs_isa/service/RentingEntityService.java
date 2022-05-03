package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.repository.RentingEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentingEntityService {
    @Autowired
    private RentingEntityRepository rentingEntityRepository;

    public RentingEntity findById(Long id) {
        return rentingEntityRepository.findById(id).orElse(null);
    }
}
