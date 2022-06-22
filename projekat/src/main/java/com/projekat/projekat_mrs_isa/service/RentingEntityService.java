package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.ReviewDisplayDTO;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.VacationHouse;

import java.util.List;


public interface RentingEntityService {

    RentingEntity findById(Long id);
    
    void save(RentingEntity rentingEntity);

    void flush();

    List<OfferDTO> getOffersByREId(RentingEntity rentingEntity);

    List<String> getPicturesByRentingEntity(RentingEntity rentingEntity);

    List<ReviewDisplayDTO> getReviewsByRentingEntityIdOrOwnerId(Long reID, Long roID);

    void deleteReviewsByRentingEntity(RentingEntity rentingEntity);
}
