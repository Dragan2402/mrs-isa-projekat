package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("select reservation from Reservation reservation where reservation.rentingEntity = ?1")
    List<Reservation> findAllFromEntity(RentingEntity entity);
}
