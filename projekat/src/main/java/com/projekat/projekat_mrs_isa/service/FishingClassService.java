package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface FishingClassService {

    @Cacheable(value = "rentingEntity",key = "#id",unless="#result == null")
    FishingClass findById(Long id);

    FishingClassDTO findFishingClassDTO(Long id);

    List<FishingClassDTO> findAllDTO();

    List<FishingClass> findAll();

    FishingClass save(FishingClass fishingClass);

    void remove(Long id);

    List<String> findPicturesByShipId(Long id);

    FishingClassDTO findDTOById(Long id);


    List<FishingClassDTO> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page);


    List<FishingClassDTO> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page);
}
