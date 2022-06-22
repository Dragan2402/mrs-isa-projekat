package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.ShipOwner;

import java.util.List;
import java.util.Map;


public interface ShipOwnerService {

    ShipOwner findById(Long id);

    ShipOwner findByUsername(String username);

    void remove(Long id);

    ShipOwner addShipOwner(Map<String, Object> userMap);

    List<ShipOwner> findAll();

    ShipOwner save(ShipOwner shipOwner);

    List<ReservationDTO> getReservationsFromOwner(ShipOwner owner);
    
    List<UserDTO> findAllDTO();

}
