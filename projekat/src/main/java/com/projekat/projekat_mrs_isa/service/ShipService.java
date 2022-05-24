package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.model.Ship;
import java.util.List;


public interface ShipService {

    Ship findById(Long id);

    List<Ship> findAll();

    Ship save(Ship ship);

    List<ShipDTO> findAllDTO();

    void remove(Long id);

    List<String> findPicturesByShipId(Long shipId);

    ShipDTO findDTOById(Long id);
}
