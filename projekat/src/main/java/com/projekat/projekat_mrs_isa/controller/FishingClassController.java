package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import com.projekat.projekat_mrs_isa.service.FishingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/fishingClasses")
public class FishingClassController {

    @Autowired
    private FishingClassService fishingClassService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<FishingClassDTO>> getAllFishingClasses() {
        List<FishingClassDTO> fishingClasses = fishingClassService.findAllDTO();
        return new ResponseEntity<>(fishingClasses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FishingClassDTO> getFishingClassDTOById(@PathVariable("id") Long id) {
        FishingClassDTO fishingClassDTO = fishingClassService.findFishingClassDTO(id);
        if (fishingClassDTO == null)
            return new ResponseEntity<FishingClassDTO>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<FishingClassDTO>(fishingClassDTO, HttpStatus.OK);
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FishingClassDTO> updateFishingClass(@RequestBody FishingClassDTO fishingClassDTO) throws Exception {
        FishingClass fishingClassToUpdate = fishingClassService.findById(2L);
        fishingClassToUpdate.update(fishingClassDTO);
        FishingClass updatedFishingClass = fishingClassService.save(fishingClassToUpdate);
        if (updatedFishingClass == null)
            return new ResponseEntity<FishingClassDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<FishingClassDTO>(new FishingClassDTO(updatedFishingClass), HttpStatus.OK);
    }
}
