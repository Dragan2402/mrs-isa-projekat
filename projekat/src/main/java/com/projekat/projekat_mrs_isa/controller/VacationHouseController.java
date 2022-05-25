package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.service.OfferService;
import com.projekat.projekat_mrs_isa.service.UtilityService;
import com.projekat.projekat_mrs_isa.service.VacationHouseService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/vacation_houses")
public class VacationHouseController {
    @Autowired
    private VacationHouseService vacationHouseService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private ResourceLoader resourceLoader;


    @GetMapping
    @Transactional
    public ResponseEntity<List<VacationHouseDTO>> getAllVacationHouses(@PathParam("address") String address,
                                                                       @PathParam("name") String name,
                                                                       @PathParam("start") String start,
                                                                       @PathParam("end") String end,
                                                                       @PathParam("people") Integer people,
                                                                       @PathParam("priceMin") Double priceMin,
                                                                       @PathParam("priceMax") Double priceMax,
                                                                       Pageable page) {

        if(address==null)
            address="";
        if(name==null)
            name="";
        LocalDateTime startDate,endDate;
        if(people==null)
            people=0;
        if(priceMin==null)
            priceMin=0.0;
        if(priceMax==null)
            priceMax=1000.0;
        if(start==null || end==null)
            return new ResponseEntity<>(vacationHouseService.findByNoDateCriteria(name,address,people,priceMin,priceMax,page),HttpStatus.OK);
        else{

            LocalDateTime startDateTemp = LocalDateTime.parse(start, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            startDate = startDateTemp.plusHours(2);
            LocalDateTime endTemp = LocalDateTime.parse(end, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            endDate=endTemp.plusHours(2);
            return new ResponseEntity<>(vacationHouseService.findByCriteria(name,address,startDate,endDate,people,priceMin,priceMax,page),HttpStatus.OK);
        }

    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<VacationHouseDTO> getVacationHouse(@PathVariable("id") Long id) {
        VacationHouse vacationHouse=vacationHouseService.findById(id);
        if (vacationHouse==null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(vacationHouseService.findDTOById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
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
   //@PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
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
    //@PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
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
    @PreAuthorize("hasRole('VH_OWNER')")
    @Transactional
    public ResponseEntity<VacationHouseDTO> updateVacationHouse(@RequestBody VacationHouseDTO vacationHouseDTO) {
        LocalDateTime availableFrom = vacationHouseDTO.getAvailableFrom();
        LocalDateTime availableTo = vacationHouseDTO.getAvailableTo();
//        if(availableFrom.isAfter(availableTo) || availableFrom.isBefore(LocalDateTime.now()))
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        VacationHouse vacationHouse = vacationHouseService.findById(vacationHouseDTO.getId());
        vacationHouse.update(vacationHouseDTO);
        vacationHouse = vacationHouseService.save(vacationHouse);
        if (vacationHouse == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new VacationHouseDTO(vacationHouse), HttpStatus.OK);
    }
}
