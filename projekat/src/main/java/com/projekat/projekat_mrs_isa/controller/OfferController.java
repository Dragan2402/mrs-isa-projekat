package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.service.FishingClassService;
import com.projekat.projekat_mrs_isa.service.OfferService;
import com.projekat.projekat_mrs_isa.service.ShipService;
import com.projekat.projekat_mrs_isa.service.VacationHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api/offers")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @Autowired
    private VacationHouseService vacationHouseService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private FishingClassService fishingClassService;

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO) {
        Long rentingEntityId = offerDTO.getRentingEntityId();
        RentingEntity rentingEntity = vacationHouseService.findById(rentingEntityId);
        if (rentingEntity == null) rentingEntity = shipService.findById(rentingEntityId);
        if (rentingEntity == null) rentingEntity = fishingClassService.findById(rentingEntityId);
        if (rentingEntity == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Offer newOffer = new Offer(offerDTO, rentingEntity);
        offerService.save(newOffer);
        return new ResponseEntity<>(offerDTO, HttpStatus.OK);
    }
}
