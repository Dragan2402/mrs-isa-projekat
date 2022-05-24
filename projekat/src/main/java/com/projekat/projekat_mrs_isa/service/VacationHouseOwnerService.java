package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import java.util.List;


public interface VacationHouseOwnerService {

    VacationHouseOwner findById(Long id);

    List<VacationHouseOwner> findAll();

    VacationHouseOwner save(VacationHouseOwner vacationHouseOwner);

    void remove(Long id);
}
