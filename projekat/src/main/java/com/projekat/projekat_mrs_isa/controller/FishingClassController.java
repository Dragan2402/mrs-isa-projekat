package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.service.FishingClassService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/fishingClasses")
public class FishingClassController {

    @Autowired
    private FishingClassService fishingClassService;
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(value = "/all")
    @Transactional
    public ResponseEntity<List<FishingClassDTO>> getAllFishingClasses() {
        List<FishingClass> fishingClasses = fishingClassService.findAll();
        List<FishingClassDTO> fishingClassDTOS=new ArrayList<>();
        for (FishingClass fishingClass : fishingClasses){
            FishingClassDTO fishingClassDTO= new FishingClassDTO(fishingClass);
            fishingClassDTO.setImg(encodeImage(fishingClass));
            fishingClassDTOS.add(fishingClassDTO);
        }
        return new ResponseEntity<>(fishingClassDTOS, HttpStatus.OK);
    }

    public String encodeImage(RentingEntity rentingEntity){
        String picturePath="pictures/renting_entities/0.png";
        if(rentingEntity.getPictures().size() > 0){
            picturePath=rentingEntity.getPictures().get(0);
        }

        Resource r = resourceLoader.getResource("classpath:" + picturePath);
        try {
            File file = r.getFile();
            byte[] picture = FileUtils.readFileToByteArray(file);
            return Base64.encodeBase64String(picture);

        } catch (IOException e) {
            return "ERROR";
        }


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


    @GetMapping(value = "/{id}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<OfferDTO>> getOffers(@PathVariable("id") Long id) {
        FishingClass fishingClass = fishingClassService.findById(id);
        if (fishingClass == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OfferDTO> offers=new ArrayList<>();
        for(Offer offer : fishingClass.getOffers()) {
            if (offer.getStart().compareTo(LocalDateTime.now()) > 0) {
                OfferDTO temp = new OfferDTO(offer);
                offers.add(temp);

            }else{

                offer.setDeleted(true);


            }
        }
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}/pictures/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<String>> getAllPictures(@PathVariable("id") Long id) {
        List<String> picturePaths = fishingClassService.findPicturesByShipId(id);
        List<String> encodedPictures = new ArrayList<>();

        for (String picturePath: picturePaths) {
            Resource r = resourceLoader
                    .getResource("classpath:" + picturePath);
            try {
                File file = r.getFile();
                byte[] picture = FileUtils.readFileToByteArray(file);
                String encodedPicture = Base64.encodeBase64String(picture);
                encodedPictures.add(encodedPicture);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(encodedPictures, HttpStatus.OK);
    }


}
