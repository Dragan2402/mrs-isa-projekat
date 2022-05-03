package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.Ship;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ShipDTO>> getAllShips() {
        List<ShipDTO> ships = shipService.findAllDTO();
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShipDTO> getShip(@PathVariable("id") Long id) {
        Ship ship = shipService.findById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ShipDTO(ship), HttpStatus.OK);

    }

    @GetMapping(value = "/{id}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<OfferDTO>> getOffers(@PathVariable("id") Long id) {
        Ship ship = shipService.findById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OfferDTO> offers=new ArrayList<>();
        for(Offer offer : ship.getOffers()) {
            if (offer.getStart().compareTo(LocalDateTime.now()) > 0) {
                OfferDTO temp = new OfferDTO(offer);
                offers.add(temp);

            }else{

                offer.setDeleted(true);


            }
        }
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }
}
