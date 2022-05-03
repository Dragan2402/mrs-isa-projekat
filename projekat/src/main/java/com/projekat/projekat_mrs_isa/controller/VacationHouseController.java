package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.service.OfferService;
import com.projekat.projekat_mrs_isa.service.VacationHouseService;
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
@RequestMapping(value = "api/vacation_houses")
public class VacationHouseController {
    @Autowired
    private VacationHouseService vacationHouseService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(value = "/all")
    @Transactional
    public ResponseEntity<List<VacationHouseDTO>> getAllVacationHouses() {
        List<VacationHouse> vacationHouses = vacationHouseService.findAll();
        List<VacationHouseDTO> vacationHouseDTOS=new ArrayList<>();
        for (VacationHouse vacationHouse : vacationHouses){
            VacationHouseDTO vacationHouseDTO= new VacationHouseDTO(vacationHouse);
            vacationHouseDTO.setImg(encodeImage(vacationHouse));
            vacationHouseDTOS.add(vacationHouseDTO);
        }
        return new ResponseEntity<>(vacationHouseDTOS, HttpStatus.OK);
    }

    public String encodeImage(RentingEntity rentingEntity){
        String picturePath="pictures/renting_entities/0.png";
        if(rentingEntity.getPictures().size()>0){
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
    @Transactional
    public ResponseEntity<VacationHouseDTO> getVacationHouse(@PathVariable("id") Long id) {
        VacationHouse vacationHouse = vacationHouseService.findById(id);
        if (vacationHouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new VacationHouseDTO(vacationHouse), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<OfferDTO>> getOffers(@PathVariable("id") Long id) {
        VacationHouse vacationHouse = vacationHouseService.findById(id);
        if (vacationHouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OfferDTO> offers=new ArrayList<>();
        for(Offer offer : vacationHouse.getOffers()) {
            if (offer.getStart().compareTo(LocalDateTime.now()) > 0) {
                OfferDTO temp = new OfferDTO(offer);
                offers.add(temp);

            }else{

                offer.setDeleted(true);


            }
            }
        return new ResponseEntity<>(offers, HttpStatus.OK);
        }


    @GetMapping(value = "/{vacationHouseId}/pictures/{pictureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<String> getPicture(@PathVariable("vacationHouseId") Long vacationHouseId, @PathVariable("pictureId") Long pictureId) {
        Resource r = resourceLoader
                .getResource("classpath:pictures/renting_entities/" + vacationHouseId + "/" + pictureId + ".jpg");
        try {
            File file = r.getFile();
            byte[] picture = FileUtils.readFileToByteArray(file);
            String encodedPicture = Base64.encodeBase64String(picture);
            return new ResponseEntity<>(encodedPicture, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{vacationHouseId}/pictures/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<String>> getAllPictures(@PathVariable("vacationHouseId") Long vacationHouseId) {
        List<String> picturePaths = vacationHouseService.findPicturesByVacationHouseId(vacationHouseId);
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<VacationHouseDTO> updateVacationHouse(@RequestBody VacationHouseDTO vacationHouseDTO) {
        VacationHouse vacationHouse = vacationHouseService.findById(vacationHouseDTO.getId());
        vacationHouse.update(vacationHouseDTO);
        vacationHouse = vacationHouseService.save(vacationHouse);
        if (vacationHouse == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new VacationHouseDTO(vacationHouse), HttpStatus.OK);
    }
}
