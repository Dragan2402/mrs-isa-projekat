package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.User;

import java.util.List;

public interface ReservationService {


    Reservation findById(Long id);

    Reservation save(Reservation reservation);

    Boolean cancelReservation(Long id);
}
