package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.repository.RentingEntityRepository;
import com.projekat.projekat_mrs_isa.service.RentingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentingEntityServiceImpl implements RentingEntityService {
    @Autowired
    private RentingEntityRepository rentingEntityRepository;

    @Override
    public RentingEntity findById(Long id) {
        return rentingEntityRepository.findById(id).orElse(null);
    }
}
