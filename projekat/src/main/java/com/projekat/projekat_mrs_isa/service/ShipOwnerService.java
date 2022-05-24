package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.ShipOwner;
import java.util.List;
import java.util.Map;


public interface ShipOwnerService {

    ShipOwner findById(Long id);

    void remove(Long id);

    ShipOwner addShipOwner(Map<String, Object> userMap);

    List<ShipOwner> findAll();

    ShipOwner save(ShipOwner shipOwner);

}
