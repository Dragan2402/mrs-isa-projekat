package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import com.projekat.projekat_mrs_isa.repository.FishingClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishingClassService {
    @Autowired
    private FishingClassRepository fishingClassRepository;

    public FishingClass findById(Long id) {
        return fishingClassRepository.findById(id).orElse(null);
    }

    public FishingClassDTO findFishingClassDTO(Long id) { return  fishingClassRepository.findFishingClassDTO(id) ; }

    public List<FishingClassDTO> findAllDTO() { return fishingClassRepository.findAllDTO(); }

    public List<FishingClass> findAll() {
        return fishingClassRepository.findAll();
    }

    public FishingClass save(FishingClass fishingClass) {
        return fishingClassRepository.save(fishingClass);
    }

    public void remove(Long id) {
        fishingClassRepository.deleteById(id);
    }
}
