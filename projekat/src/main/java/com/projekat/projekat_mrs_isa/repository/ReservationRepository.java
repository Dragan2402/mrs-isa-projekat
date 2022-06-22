package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("select r from Reservation r join fetch r.rentingEntity join fetch r.client where r.rentingEntity = ?1 and r.deleted=false")
    List<Reservation> findAllFromEntity(RentingEntity entity);


    @Query("select reservation from Reservation reservation where reservation.client = ?1 and reservation.deleted = true")
    List<Reservation> findAllCanceledReservationClient(Client client);

    @Query("select reservation from Reservation reservation where reservation.client = ?1 and reservation.deleted=false")
    List<Reservation> getByClient(Client client);


    @Query("select new com.projekat.projekat_mrs_isa.dto.ReservationDTO(r) from Reservation r where r.deleted=false")
    List<ReservationDTO> findAllDTO();

    @Query("select r from Reservation r where r.start >= ?1 and r.duration < ?2 and r.rentingEntity = ?3 and r.deleted=false")
    List<Reservation> getReservationsByDateAndEntity(LocalDateTime dateStart, Duration duration, RentingEntity rentingEntity);

//    @Query("select r from Reservation r join fetch r.rentingEntity join fetch r.client where r.rentingEntity = ?1")
//    List<Reservation> getAllFromEntity(RentingEntity entity);
}
// rentingEntity
// client