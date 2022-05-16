package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation save(Reservation reservation) { return reservationRepository.save(reservation); }
}
