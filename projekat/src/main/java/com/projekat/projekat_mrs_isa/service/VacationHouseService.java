package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.List;


public interface VacationHouseService {


    VacationHouse findById(Long id);

    List<VacationHouse> findAll();

    List<VacationHouse> findAllFromOwner(String ownerUsername);

    List<VacationHouseDTO> findAllDTO();

    Page<VacationHouse> findAll(Pageable page);

    VacationHouse save(VacationHouse vacationHouse);

    void remove(Long id);

    List<String> findPicturesByVacationHouseId(Long shipId);

    VacationHouseDTO findDTOById(Long id);

    List<VacationHouseDTO> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page);

    List<VacationHouseDTO> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page);
    
    List<Reservation> findAllReservations(Long vacationHouseId);
}
