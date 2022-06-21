package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.service.UtilityService;
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

    @Autowired
    private UtilityService utilityService;

    @GetMapping(value = "loggedVacationHouseOwner", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('VH_OWNER')")
    public ResponseEntity<UserDTO> getLoggedVacationHouseOwner(Principal vhoPrincipal) {
        VacationHouseOwner vho = vacationHouseOwnerService.findByUsername(vhoPrincipal.getName());
        if (vho == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new UserDTO(vho), HttpStatus.OK);
    }

    @GetMapping(value = "loggedVacationHouseOwner/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('VH_OWNER')")
    public ResponseEntity<List<ReservationDTO>> getReservations(Principal ownerPrincipal) {
        VacationHouseOwner owner = vacationHouseOwnerService.findByUsername(ownerPrincipal.getName());
        if (owner == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<ReservationDTO> reservations = vacationHouseOwnerService.getReservationsFromOwner(owner);
        for (ReservationDTO r: reservations){
            String imgPath = r.getImg();
            r.setImg(utilityService.getPictureEncoded(imgPath));
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/getServices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<String>> rentingOwnerServices(@PathVariable("id") Long id) {
        VacationHouseOwner owner = vacationHouseOwnerService.findById(id);
        if (owner == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(owner.getAdditionalServices(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('VH_OWNER')")
    @Transactional
    public ResponseEntity<UserDTO> updateVacationHouseOwner(@RequestBody UserDTO userDTO) {
        VacationHouseOwner vacationHouseOwner = vacationHouseOwnerService.findById(userDTO.getId());
        vacationHouseOwner.update(userDTO);
        vacationHouseOwner = vacationHouseOwnerService.save(vacationHouseOwner);
        if (vacationHouseOwner == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new UserDTO(vacationHouseOwner), HttpStatus.OK);
    }
}
