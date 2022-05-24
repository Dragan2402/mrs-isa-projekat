package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.ShipOwner;
import java.util.List;


public interface ShipOwnerService {


    ShipOwner findById(Long id);

    List<ShipOwner> findAll();

    ShipOwner save(ShipOwner shipOwner);

    void remove(Long id);
}
