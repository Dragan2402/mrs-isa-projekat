package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.ShipOwner;
import com.projekat.projekat_mrs_isa.repository.ShipOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipOwnerService {
    @Autowired
    private ShipOwnerRepository shipOwnerRepository;

    public ShipOwner findById(Long id) {
        return shipOwnerRepository.findById(id).orElse(null);
    }

    public List<ShipOwner> findAll() {
        return shipOwnerRepository.findAll();
    }

    public ShipOwner save(ShipOwner shipOwner) {
        return shipOwnerRepository.save(shipOwner);
    }

    public void remove(Long id) {
        shipOwnerRepository.deleteById(id);
    }
}
