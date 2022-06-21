package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.ReviewDisplayDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.service.OfferService;
import com.projekat.projekat_mrs_isa.service.RentingEntityService;
import com.projekat.projekat_mrs_isa.service.UtilityService;
import com.projekat.projekat_mrs_isa.service.VacationHouseOwnerService;
import com.projekat.projekat_mrs_isa.service.VacationHouseService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/vacationHouses")
public class VacationHouseController {
    @Autowired
    private VacationHouseService vacationHouseService;
    @Autowired
    private VacationHouseOwnerService vacationHouseOwnerService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private ResourceLoader resourceLoader;


    @Autowired
    private RentingEntityService rentingEntityService;

    @GetMapping(value = "/anyUser/**")
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

    @NotNull
    private List<VacationHouseDTO> getVacationHouseDTOList(List<VacationHouse> vacationHouses) {
        return vacationHouseService.convertToDto(vacationHouses);
    }

    
   @GetMapping(value = "/anyUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<VacationHouseDTO> getVacationHouse(@PathVariable("id") Long id) {
        VacationHouse vacationHouse=vacationHouseService.findById(id);
        if (vacationHouse==null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(vacationHouseService.findDTOById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/anyUser/{id}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<OfferDTO>> getOffers(@PathVariable("id") Long id) {
        VacationHouse vacationHouse = vacationHouseService.findById(id);
        if (vacationHouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rentingEntityService.getOffersByREId(vacationHouse),HttpStatus.OK);
    }

    @GetMapping(value = "/anyUser/{id}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<ReviewDisplayDTO>> getReviews(@PathVariable("id") Long id) {
        VacationHouse vacationHouse = vacationHouseService.findById(id);
        if (vacationHouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rentingEntityService.getReviewsByRentingEntityIdOrOwnerId(vacationHouse.getId(),vacationHouse.getVacationHouseOwner().getId()),HttpStatus.OK);
    }



    @GetMapping(value = "/anyUser/{vacationHouseId}/pictures/all", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<List<String>> getAllPictures(@PathVariable("vacationHouseId") Long vacationHouseId) {
        VacationHouse vacationHouse = vacationHouseService.findById(vacationHouseId);
        if (vacationHouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rentingEntityService.getPicturesByRentingEntity(vacationHouse),HttpStatus.OK);
    }

    @PostMapping(value = "/loggedVacationHouseOwner/")
    @PreAuthorize("hasRole('VH_OWNER')")
    public ResponseEntity<Boolean> createVacationHouse(@RequestBody VacationHouseDTO vacationHouseDTO, Principal ownerPrincipal) {
        VacationHouseOwner owner = vacationHouseOwnerService.findByUsername(ownerPrincipal.getName());
        if(owner == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        VacationHouse vacationHouse = new VacationHouse(vacationHouseDTO);
        owner.addVacationHouse(vacationHouse);

        vacationHouseService.save(vacationHouse);
        vacationHouseOwnerService.save(owner);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping(value = "/loggedVacationHouseOwner/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('VH_OWNER')")
    @Transactional
    public ResponseEntity<VacationHouseDTO> updateVacationHouse(@RequestBody VacationHouseDTO vacationHouseDTO, @PathVariable("id") Long id) {
        LocalDateTime availableFrom = vacationHouseDTO.getAvailableFrom();
        LocalDateTime availableTo = vacationHouseDTO.getAvailableTo();
        if(availableFrom.isAfter(availableTo))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Reservation> reservations = vacationHouseService.findAllReservations(id);
        if(reservations.size() != 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        VacationHouse vacationHouse = vacationHouseService.findById(vacationHouseDTO.getId());
        if (vacationHouse == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        vacationHouse.update(vacationHouseDTO);
        vacationHouse = vacationHouseService.save(vacationHouse);
        if (vacationHouse == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new VacationHouseDTO(vacationHouse), HttpStatus.OK);
    }

    @GetMapping(value = "/loggedVacationHouseOwner/all")
    @PreAuthorize("hasRole('VH_OWNER')")
    @Transactional
    public ResponseEntity<List<VacationHouseDTO>> getAllVacationHousesFromOwner(Principal ownerPrincipal) {
        List<VacationHouse> vacationHouses = vacationHouseService.findAllFromOwner(ownerPrincipal.getName());
        List<VacationHouseDTO> vacationHouseDTOs = getVacationHouseDTOList(vacationHouses);
        return new ResponseEntity<>(vacationHouseDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/loggedVacationHouseOwner/{id}/hasReservations")
    @PreAuthorize("hasRole('VH_OWNER')")
    public ResponseEntity<Boolean> hasReservations(@PathVariable("id") Long id) {
        List<Reservation> reservations = vacationHouseService.findAllReservations(id);
        Boolean hasReservations = reservations.size() != 0;
        return new ResponseEntity<>(hasReservations, HttpStatus.OK);
    }

    @DeleteMapping(value = "/loggedVacationHouseOwner/{id}")
    @PreAuthorize("hasRole('VH_OWNER')")
//    @Transactional
    public ResponseEntity<VacationHouseDTO> deleteVacationHouse(@PathVariable("id") Long id, Principal ownerPrincipal) {
        List<Reservation> reservations = vacationHouseService.findAllReservations(id);
        if(reservations.size() != 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        VacationHouse vacationHouse = vacationHouseService.findById(id);
        String username = vacationHouse.getVacationHouseOwner().getUsername();
        if(!username.equals(ownerPrincipal.getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        vacationHouseService.remove(id);
        return new ResponseEntity<>(new VacationHouseDTO(vacationHouse), HttpStatus.OK);
    }
}
