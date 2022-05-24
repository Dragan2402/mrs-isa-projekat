package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import java.util.List;


public interface VacationHouseService {


    VacationHouse findById(Long id);

    List<VacationHouse> findAll();

    List<VacationHouseDTO> findAllDTO();

    VacationHouse save(VacationHouse vacationHouse);

    void remove(Long id);

    List<String> findPicturesByVacationHouseId(Long shipId);

    VacationHouseDTO findDTOById(Long id);
}
