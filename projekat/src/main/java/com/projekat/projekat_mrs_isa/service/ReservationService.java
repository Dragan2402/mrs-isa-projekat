package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface ReservationService {


    Reservation findById(Long id);

    Reservation save(Reservation reservation);
}
