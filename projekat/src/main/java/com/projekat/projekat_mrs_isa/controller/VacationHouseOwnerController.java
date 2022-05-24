package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.TakenPeriodDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.service.VacationHouseOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/vacationHouseOwners")
public class VacationHouseOwnerController {
    @Autowired
    private VacationHouseOwnerService vacationHouseOwnerService;

    @GetMapping(value = "/getServices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<String>> rentingOwnerServices(@PathVariable("id") Long id) {
        VacationHouseOwner owner = vacationHouseOwnerService.findById(id);
        if (owner == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(owner.getAdditionalServices(), HttpStatus.OK);
    }
}
