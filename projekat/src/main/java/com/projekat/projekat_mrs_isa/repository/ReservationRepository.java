package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("select r from Reservation r join fetch r.rentingEntity join fetch r.client where r.rentingEntity = ?1")
    List<Reservation> getAllFromEntity(RentingEntity entity);
}
// rentingEntity
// client