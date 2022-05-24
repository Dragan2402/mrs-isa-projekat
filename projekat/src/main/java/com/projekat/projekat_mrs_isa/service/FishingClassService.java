package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import java.util.List;


public interface FishingClassService {

    FishingClass findById(Long id);

    FishingClassDTO findFishingClassDTO(Long id);

    List<FishingClassDTO> findAllDTO();

    List<FishingClass> findAll();

    FishingClass save(FishingClass fishingClass);

    void remove(Long id);

    List<String> findPicturesByShipId(Long id);

    FishingClassDTO findDTOById(Long id);
}
