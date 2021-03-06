package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.repository.RentingEntityRepository;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    RentingEntityRepository rentingEntityRepository;

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<ReservationDTO> findAllDTO() { return reservationRepository.findAllDTO(); }

    @Override
    public Reservation save(Reservation reservation) { return reservationRepository.save(reservation); }

    @Override
    @Transactional
    public Boolean cancelReservation(Long id) throws OptimisticLockingFailureException {
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

    @Override
    public List<Reservation> getReservationsByDateAndEntity(LocalDateTime dateStart, LocalDateTime dateEnd, RentingEntity rentingEntity) {
        Duration duration=Duration.between(dateStart,dateEnd);
        return reservationRepository.getReservationsByDateAndEntity(dateStart,duration,rentingEntity);
    }
}
