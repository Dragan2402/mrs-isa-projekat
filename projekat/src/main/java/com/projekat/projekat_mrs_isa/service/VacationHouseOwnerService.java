package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.repository.VacationHouseOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationHouseOwnerService {
    @Autowired
    private VacationHouseOwnerRepository vacationHouseOwnerRepository;

    public VacationHouseOwner findById(Long id) {
        return vacationHouseOwnerRepository.findById(id).orElse(null);
    }

    public List<VacationHouseOwner> findAll() {
        return vacationHouseOwnerRepository.findAll();
    }

    public VacationHouseOwner save(VacationHouseOwner vacationHouseOwner) {
        return vacationHouseOwnerRepository.save(vacationHouseOwner);
    }

    public void remove(Long id) {
        vacationHouseOwnerRepository.deleteById(id);
    }
}
