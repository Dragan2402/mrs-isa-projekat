package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select offer from Offer offer where offer.rentingEntity = ?1 and offer.deleted=false")
    List<Offer> getOffersByRentingEntity(RentingEntity rentingEntity);
}
