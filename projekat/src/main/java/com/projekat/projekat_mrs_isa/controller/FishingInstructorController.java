package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.service.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/fishingInstructors")
public class FishingInstructorController {
    @Autowired
    private FishingInstructorService fishingInstructorService;

    @GetMapping(value = "/getServices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<String>> fishingInstructorServices(@PathVariable("id") Long id) {
        FishingInstructor instructor = fishingInstructorService.findById(id);
        if (instructor == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(instructor.getAdditionalServices(), HttpStatus.OK);
    }
}
