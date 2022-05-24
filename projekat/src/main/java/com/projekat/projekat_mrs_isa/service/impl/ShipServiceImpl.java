package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.model.Ship;
import com.projekat.projekat_mrs_isa.repository.ShipRepository;
import com.projekat.projekat_mrs_isa.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    private ShipRepository shipRepository;

    @Override
    public Ship findById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ship> findAll() {
        return shipRepository.findAll();
    }

    @Override
    public Ship save(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public List<ShipDTO> findAllDTO() { return shipRepository.findAllDTO(); }

    @Override
    public void remove(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public List<String> findPicturesByShipId(Long shipId) {
        Ship ship = shipRepository.findById(shipId).orElse(null);
        if (ship != null)
            return ship.getPictures();
        return new ArrayList<>();
    }

    @Override
    public ShipDTO findDTOById(Long id) {
        Ship ship = this.findById(id);
        if (ship == null) {
            return null;
        }
        ShipDTO shipDTO= new ShipDTO(ship);
        shipDTO.setOwnerId(ship.getShipOwner().getId());
        return shipDTO;
    }
}
