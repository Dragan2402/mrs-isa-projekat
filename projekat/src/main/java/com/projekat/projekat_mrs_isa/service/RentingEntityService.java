package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.RentingEntity;


public interface RentingEntityService {
    RentingEntity findById(Long id);

    void save(RentingEntity rentingEntity);

    void flush();
}
