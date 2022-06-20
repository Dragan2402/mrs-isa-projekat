package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.ShipOwner;
import com.projekat.projekat_mrs_isa.service.ShipOwnerService;
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

    @GetMapping(value = "loggedShipOwner", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SHIP_OWNER')")
//    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    public ResponseEntity<UserDTO> getLoggedShipOwner(Principal soPrincipal) {
        ShipOwner so = shipOwnerService.findByUsername(soPrincipal.getName());
        if (so == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new UserDTO(so), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SHIP_OWNER')")
    @Transactional
    public ResponseEntity<UserDTO> updateShipOwner(@RequestBody UserDTO userDTO) {
        ShipOwner so = shipOwnerService.findById(userDTO.getId());
        so.update(userDTO);
        so = shipOwnerService.save(so);
        if (so == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new UserDTO(so), HttpStatus.OK);
    }
}
