package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import com.projekat.projekat_mrs_isa.repository.FishingClassRepository;
import com.projekat.projekat_mrs_isa.service.FishingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FishingClassServiceImpl implements FishingClassService {
    @Autowired
    private FishingClassRepository fishingClassRepository;

    @Override
    public FishingClass findById(Long id) {
        return fishingClassRepository.findById(id).orElse(null);
    }

    @Override
    public FishingClassDTO findFishingClassDTO(Long id) { return  fishingClassRepository.findFishingClassDTO(id) ; }

    @Override
    public List<FishingClassDTO> findAllDTO() { return fishingClassRepository.findAllDTO(); }

    @Override
    public List<FishingClass> findAll() {
        return fishingClassRepository.findAll();
    }

    @Override
    public FishingClass save(FishingClass fishingClass) {
        return fishingClassRepository.save(fishingClass);
    }

    @Override
    public void remove(Long id) {
        fishingClassRepository.deleteById(id);
    }

    @Override
    public List<String> findPicturesByShipId(Long id) {
        FishingClass fishingClass = fishingClassRepository.findById(id).orElse(null);
        if (fishingClass != null)
            return fishingClass.getPictures();
        else
            return new ArrayList<>();
    }

    @Override
    public FishingClassDTO findDTOById(Long id) {
        FishingClass fishingClass = this.findById(id);
        if (fishingClass == null) {
            return null;
        }
        FishingClassDTO fishingClassDTO= new FishingClassDTO(fishingClass);
        fishingClassDTO.setOwnerId(fishingClass.getFishingInstructor().getId());
        return fishingClassDTO;
    }
}
