package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;

import java.util.List;
import java.util.Map;



public interface VacationHouseOwnerService {

    VacationHouseOwner findById(Long id);

    VacationHouseOwner findByUsername(String username);

    List<VacationHouseOwner> findAll();

    VacationHouseOwner addVacationHouseOwner(Map<String, Object> userMap);

    VacationHouseOwner save(VacationHouseOwner vacationHouseOwner);

    void remove(Long id);
}
