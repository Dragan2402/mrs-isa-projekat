package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Ship;
import com.projekat.projekat_mrs_isa.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipService {
    @Autowired
    private ShipRepository shipRepository;

    public Ship findById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    public List<Ship> findAll() {
        return shipRepository.findAll();
    }

    public Ship save(Ship ship) {
        return shipRepository.save(ship);
    }

    public void remove(Long id) {
        shipRepository.deleteById(id);
    }
}
