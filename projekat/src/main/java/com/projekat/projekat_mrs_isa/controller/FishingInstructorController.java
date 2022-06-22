package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.service.FishingInstructorService;
import com.projekat.projekat_mrs_isa.service.UtilityService;
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
@RequestMapping(value = "api/fishingInstructors")
public class FishingInstructorController {
    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private UtilityService utilityService;

    @GetMapping(value = "/getServices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<String>> fishingInstructorServices(@PathVariable("id") Long id) {
        FishingInstructor instructor = fishingInstructorService.findById(id);
        if (instructor == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(instructor.getAdditionalServices(), HttpStatus.OK);
    }

    @GetMapping(value = "loggedFishingInstructor", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    public ResponseEntity<UserDTO> getLoggedFishingInstructor(Principal instructorPrincipal) {
        FishingInstructor fishingInstructor = fishingInstructorService.findByUsername(instructorPrincipal.getName());
        if (fishingInstructor == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new UserDTO(fishingInstructor), HttpStatus.OK);
    }

    @GetMapping(value = "loggedFishingInstructor/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    public ResponseEntity<List<ReservationDTO>> getReservations(Principal instructorPrincipal) {
        FishingInstructor fishingInstructor = fishingInstructorService.findByUsername(instructorPrincipal.getName());
        if (fishingInstructor == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<ReservationDTO> reservations = fishingInstructorService.getReservationsFromInstructor(fishingInstructor);
        for (ReservationDTO r: reservations){
            String imgPath = r.getImg();
            r.setImg(utilityService.getPictureEncoded(imgPath));
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<UserDTO> updateFishingInstructor(@RequestBody UserDTO userDTO) {
        FishingInstructor fishingInstructor = fishingInstructorService.findById(userDTO.getId());
        fishingInstructor.update(userDTO);
        fishingInstructor = fishingInstructorService.save(fishingInstructor);
        if (fishingInstructor == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new UserDTO(fishingInstructor), HttpStatus.OK);
    }
}
