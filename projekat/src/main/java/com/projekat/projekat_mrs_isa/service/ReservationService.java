package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import org.springframework.dao.OptimisticLockingFailureException;
import com.projekat.projekat_mrs_isa.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {


    Reservation findById(Long id);

    List<ReservationDTO> findAllDTO();

    Reservation save(Reservation reservation);

    Boolean cancelReservation(Long id) throws OptimisticLockingFailureException;

    List<Reservation> getReservationsByDateAndEntity(LocalDateTime dateStart, LocalDateTime dateEnd, RentingEntity rentingEntity);
}
