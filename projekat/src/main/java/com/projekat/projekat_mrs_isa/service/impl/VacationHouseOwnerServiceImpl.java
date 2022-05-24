package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.repository.VacationHouseOwnerRepository;
import com.projekat.projekat_mrs_isa.service.VacationHouseOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationHouseOwnerServiceImpl implements VacationHouseOwnerService {
    @Autowired
    private VacationHouseOwnerRepository vacationHouseOwnerRepository;

    @Override
    public VacationHouseOwner findById(Long id) {
        return vacationHouseOwnerRepository.findById(id).orElse(null);
    }

    @Override
    public List<VacationHouseOwner> findAll() {
        return vacationHouseOwnerRepository.findAll();
    }

    @Override
    public VacationHouseOwner save(VacationHouseOwner vacationHouseOwner) {
        return vacationHouseOwnerRepository.save(vacationHouseOwner);
    }

    @Override
    public void remove(Long id) {
        vacationHouseOwnerRepository.deleteById(id);
    }
}
