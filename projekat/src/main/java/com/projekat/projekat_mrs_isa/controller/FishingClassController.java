package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Ship;
import com.projekat.projekat_mrs_isa.service.FishingClassService;
import com.projekat.projekat_mrs_isa.service.RentingEntityService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/fishingClasses")
public class FishingClassController {

    @Autowired
    private FishingClassService fishingClassService;

    @Autowired
    private RentingEntityService rentingEntityService;



    @GetMapping(value = "/anyUser/**")
    @Transactional
    public ResponseEntity<List<FishingClassDTO>> getAllFishingClasses(@PathParam("address") String address,
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
        if(start==null || end==null || start.equals("null") || end.equals("null"))
            return new ResponseEntity<>(fishingClassService.findByNoDateCriteria(name,address,people,priceMin,priceMax,page),HttpStatus.OK);
        else{

            LocalDateTime startDateTemp = LocalDateTime.parse(start, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            startDate = startDateTemp.plusHours(2);
            LocalDateTime endTemp = LocalDateTime.parse(end, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            endDate=endTemp.plusHours(2);

            return new ResponseEntity<>(fishingClassService.findByCriteria(name,address,startDate,endDate,people,priceMin,priceMax,page),HttpStatus.OK);
        }

    }



    @GetMapping(value = "/anyUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FishingClassDTO> getFishingClassDTOById(@PathVariable("id") Long id) {
        FishingClass fishingClass = fishingClassService.findById(id);
        if (fishingClass == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(fishingClassService.findDTOById(id), HttpStatus.OK);
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


    @GetMapping(value = "/anyUser/{id}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<OfferDTO>> getOffers(@PathVariable("id") Long id) {
        FishingClass fishingClass = fishingClassService.findById(id);
        if (fishingClass == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rentingEntityService.getOffersByREId(fishingClass),HttpStatus.OK);

    }


    @GetMapping(value = "/anyUser/{id}/pictures/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<String>> getAllPictures(@PathVariable("id") Long id) {
        FishingClass fishingClass = fishingClassService.findById(id);
        if (fishingClass == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rentingEntityService.getPicturesByRentingEntity(fishingClass),HttpStatus.OK);
    }


}
