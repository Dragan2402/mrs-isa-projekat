package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("select reservation from Reservation reservation where reservation.rentingEntity = ?1 and reservation.deleted=false")
    List<Reservation> findAllFromEntity(RentingEntity entity);


    @Query("select reservation from Reservation reservation where reservation.client = ?1 and reservation.deleted = true")
    List<Reservation> findAllCanceledReservationClient(Client client);

    @Query("select reservation from Reservation reservation where reservation.client = ?1 and reservation.deleted=false")
    List<Reservation> getByClient(Client client);


    @Query("select new com.projekat.projekat_mrs_isa.dto.ReservationDTO(r) from Reservation r")
    List<ReservationDTO> findAllDTO();
}
