package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.repository.VacationHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationHouseService {
    @Autowired
    private VacationHouseRepository vacationHouseRepository;

    public VacationHouse findById(Long id) {
        return vacationHouseRepository.findById(id).orElse(null);
    }

    public List<VacationHouse> findAll() {
        return vacationHouseRepository.findAll();
    }

    public List<VacationHouseDTO> findAllDTO() {
        return vacationHouseRepository.findAllDTO();
    }

    public VacationHouse save(VacationHouse vacationHouse) {
        return vacationHouseRepository.save(vacationHouse);
    }

    public void remove(Long id) {
        vacationHouseRepository.deleteById(id);
    }
}
