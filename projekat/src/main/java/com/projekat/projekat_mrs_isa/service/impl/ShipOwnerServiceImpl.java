package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.ShipOwner;
import com.projekat.projekat_mrs_isa.repository.ShipOwnerRepository;
import com.projekat.projekat_mrs_isa.service.ShipOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipOwnerServiceImpl implements ShipOwnerService {

    @Autowired
    private ShipOwnerRepository shipOwnerRepository;

    @Override
    public ShipOwner findById(Long id) {
        return shipOwnerRepository.findById(id).orElse(null);
    }

    @Override
    public List<ShipOwner> findAll() {
        return shipOwnerRepository.findAll();
    }

    @Override
    public ShipOwner save(ShipOwner shipOwner) {
        return shipOwnerRepository.save(shipOwner);
    }

    @Override
    public void remove(Long id) {
        shipOwnerRepository.deleteById(id);
    }
}
