package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.service.OfferService;
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
        List<VacationHouseDTO> vacationHouseDTOS=new ArrayList<>();
        for (VacationHouse vacationHouse : vacationHouses){
            VacationHouseDTO vacationHouseDTO= new VacationHouseDTO(vacationHouse);
            String picturePath="pictures/renting_entities/0.png";
            if(vacationHouse.getPictures().size()>0){
                picturePath=vacationHouse.getPictures().get(0);
            }
            vacationHouseDTO.setImg(utilityService.getPictureEncoded(picturePath));
            vacationHouseDTOS.add(vacationHouseDTO);
        }
        return vacationHouseDTOS;
    }

    // @GetMapping(value = "/anyUser/all")
    // //@PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    // @Transactional
    // public ResponseEntity<List<VacationHouseDTO>> getAllVacationHouses() {
    //     List<VacationHouse> vacationHouses = vacationHouseService.findAll();
    //     List<VacationHouseDTO> vacationHouseDTOs = getVacationHouseDTOList(vacationHouses);
    //     return new ResponseEntity<>(vacationHouseDTOs, HttpStatus.OK);
    // }

    
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


//    @GetMapping(value = "/anyUser/{vacationHouseId}/pictures/{pictureId}", produces = MediaType.APPLICATION_JSON_VALUE)
//   //@PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
//    @Transactional
//    public ResponseEntity<String> getPicture(@PathVariable("vacationHouseId") Long vacationHouseId, @PathVariable("pictureId") Long pictureId) {
//        Resource r = resourceLoader
//                .getResource("classpath:pictures/renting_entities/" + vacationHouseId + "/" + pictureId + ".jpg");
//        try {
//            File file = r.getFile();
//            byte[] picture = FileUtils.readFileToByteArray(file);
//            String encodedPicture = Base64.encodeBase64String(picture);
//            return new ResponseEntity<>(encodedPicture, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping(value = "/anyUser/{vacationHouseId}/pictures/all", produces = MediaType.APPLICATION_JSON_VALUE)
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
