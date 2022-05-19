package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Override
    public Boolean cancelReservation(Long id) {
        Reservation reservation=this.findById(id);
        if (reservation==null)
            return false;
        LocalDateTime dateCheck=LocalDateTime.now().plusDays(3);
        if(reservation.getStart().compareTo(dateCheck)>0){
            reservation.setDeleted(true);
            reservationRepository.save(reservation);
            return true;
        }else{
            return false;
        }
    }
}
