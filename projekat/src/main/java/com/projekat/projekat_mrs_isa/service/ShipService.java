package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.Ship;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface ShipService {

    Ship findById(Long id);

    List<Ship> findAll();

    Ship save(Ship ship);

    List<ShipDTO> findAllDTO();

    void remove(Long id);

    List<String> findPicturesByShipId(Long shipId);

    ShipDTO findDTOById(Long id);

    List<ShipDTO> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page);

    List<ShipDTO> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page);

    List<Reservation> findAllReservations(Long shipId);

    List<Ship> findAllFromOwner(String ownerUsername);
}
