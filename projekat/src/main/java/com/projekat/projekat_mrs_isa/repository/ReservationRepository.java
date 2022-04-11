package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
