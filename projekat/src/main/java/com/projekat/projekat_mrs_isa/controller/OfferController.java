package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("api/offers")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @Autowired
    private RentingEntityService rentingEntityService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO) {
        Long rentingEntityId = offerDTO.getRentingEntityId();
        RentingEntity rentingEntity = rentingEntityService.findById(rentingEntityId);
        if (rentingEntity == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Offer newOffer = new Offer(offerDTO, rentingEntity);
        offerService.save(newOffer);
        return new ResponseEntity<>(offerDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/makeQuickReservation",produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('CLIENT')")
    public  ResponseEntity<Boolean> makeReservation(Principal principal,@RequestBody OfferDTO offerDTO){
        Offer offer = offerService.findById(offerDTO.getId());
        if(offer==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Client clientLogged=clientService.findByUsername(principal.getName());
        if(clientLogged==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(clientLogged.getPenalties()==3){
            return new ResponseEntity<>(false,HttpStatus.OK);
        }
        Reservation reservation= new Reservation(offer.getPlace(),offer.getClientLimit(), new ArrayList<>(offer.getAdditionalServices()),
                offer.getPrice(),offer.getRentingEntity(),clientLogged,offer.getStart(),offer.getDuration());
        clientLogged.addReservation(reservation);
        offer.getRentingEntity().addReservation(reservation);
        reservationService.save(reservation);
        offer.setDeleted(true);
        offerService.save(offer);
        emailService.confirmReservationMail(clientLogged,reservation);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
}
