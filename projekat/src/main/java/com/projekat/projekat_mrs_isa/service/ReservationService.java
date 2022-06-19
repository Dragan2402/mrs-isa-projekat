package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.model.Reservation;
import org.springframework.dao.OptimisticLockingFailureException;

public interface ReservationService {


    Reservation findById(Long id);

    Reservation save(Reservation reservation);

    Boolean cancelReservation(Long id) throws OptimisticLockingFailureException;
}
