package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.model.ShipOwner;
import com.projekat.projekat_mrs_isa.service.ShipOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/shipOwners")
public class ShipOwnerController {
    @Autowired
    private ShipOwnerService shipOwnerService;

    @GetMapping(value = "/getServices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<String>> shipOwnerServices(@PathVariable("id") Long id) {
        ShipOwner shipOwner = shipOwnerService.findById(id);
        if (shipOwner == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(shipOwner.getAdditionalServices(), HttpStatus.OK);
    }
}
