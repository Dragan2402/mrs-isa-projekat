package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.repository.VacationHouseRepository;
import com.projekat.projekat_mrs_isa.service.VacationHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacationHouseServiceImpl implements VacationHouseService {

    @Autowired
    private VacationHouseRepository vacationHouseRepository;

    @Override
    public VacationHouse findById(Long id) {
        return vacationHouseRepository.findById(id).orElse(null);
    }


    @Override
    public List<VacationHouse> findAll() {
        return vacationHouseRepository.findAll();
    }

    @Override
    public List<VacationHouseDTO> findAllDTO() {
        return vacationHouseRepository.findAllDTO();
    }

    @Override
    public VacationHouse save(VacationHouse vacationHouse) {
        return vacationHouseRepository.save(vacationHouse);
    }

    @Override
    public void remove(Long id) {
        vacationHouseRepository.deleteById(id);
    }

    @Override
    public List<String> findPicturesByVacationHouseId(Long shipId) {
        VacationHouse vacationHouse = vacationHouseRepository.findById(shipId).orElse(null);
        if (vacationHouse != null)
            return vacationHouse.getPictures();
        else
            return new ArrayList<>();
    }

    @Override
    public VacationHouseDTO findDTOById(Long id) {
        VacationHouse vacationHouse = this.findById(id);
        if (vacationHouse == null) {
            return null;
        }
        VacationHouseDTO vacationHouseDTO= new VacationHouseDTO(vacationHouse);
        vacationHouseDTO.setOwnerId(vacationHouse.getVacationHouseOwner().getId());
        return vacationHouseDTO;
    }
}
