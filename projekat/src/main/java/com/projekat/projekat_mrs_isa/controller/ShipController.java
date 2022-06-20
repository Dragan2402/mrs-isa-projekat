package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.Ship;
import com.projekat.projekat_mrs_isa.service.RentingEntityService;
import com.projekat.projekat_mrs_isa.service.ShipService;
import com.projekat.projekat_mrs_isa.service.UtilityService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private RentingEntityService rentingEntityService;

    @GetMapping(value = "/anyUser/**")
    @Transactional
    public ResponseEntity<List<ShipDTO>> getAllShips(@PathParam("address") String address,
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
            return new ResponseEntity<>(shipService.findByNoDateCriteria(name,address,people,priceMin,priceMax,page),HttpStatus.OK);
        else{
            LocalDateTime startDateTemp = LocalDateTime.parse(start, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            startDate = startDateTemp.plusHours(2);
            LocalDateTime endTemp = LocalDateTime.parse(end, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            endDate=endTemp.plusHours(2);
            return new ResponseEntity<>(shipService.findByCriteria(name,address,startDate,endDate,people,priceMin,priceMax,page),HttpStatus.OK);
        }

    }

    @NotNull
    private List<ShipDTO> getShipDTOList(List<Ship> ships) {
        List<ShipDTO> shipDTOList = new ArrayList<>();
        for (Ship ship : ships){
            ShipDTO shipDTO = new ShipDTO(ship);
            String picturePath="src/main/resources/pictures/renting_entities/0.png";
            if(ship.getPictures().size() > 0) {
                picturePath = ship.getPictures().get(0);
            }
            shipDTO.setImg(utilityService.getPictureEncoded(picturePath));
            shipDTOList.add(shipDTO);
        }
        return shipDTOList;
    }

//    @GetMapping(value = "/all")
//    @Transactional
//    public ResponseEntity<List<ShipDTO>> getAllShips() {
//        List<Ship> ships = shipService.findAll();
//        List<ShipDTO> shipDTOS=new ArrayList<>();
//        for (Ship ship : ships){
//            ShipDTO shipDTO= new ShipDTO(ship);
//            shipDTO.setImg(encodeImage(ship));
//            shipDTOS.add(shipDTO);
//        }
//        return new ResponseEntity<>(shipDTOS, HttpStatus.OK);
//    }

    @GetMapping(value = "/anyUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ShipDTO> getShip(@PathVariable("id") Long id) {
        Ship ship = shipService.findById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shipService.findDTOById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/anyUser/{id}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<OfferDTO>> getOffers(@PathVariable("id") Long id) {
        Ship ship = shipService.findById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rentingEntityService.getOffersByREId(ship),HttpStatus.OK);
    }

//    public String encodeImage(RentingEntity rentingEntity){
//        String picturePath="pictures/renting_entities/0.png";
//        if(rentingEntity.getPictures().size() > 0){
//            picturePath=rentingEntity.getPictures().get(0);
//        }
//
//        Resource r = resourceLoader.getResource("classpath:" + picturePath);
//        try {
//            File file = r.getFile();
//            byte[] picture = FileUtils.readFileToByteArray(file);
//            return Base64.encodeBase64String(picture);
//
//        } catch (IOException e) {
//            return "ERROR";
//        }
//
//
//    }

    @GetMapping(value = "/anyUser/{shipId}/pictures/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<String>> getAllPictures(@PathVariable("shipId") Long shipId) {
        Ship ship = shipService.findById(shipId);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rentingEntityService.getPicturesByRentingEntity(ship),HttpStatus.OK);
    }

    @PutMapping(value = "/loggedShipOwner/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SHIP_OWNER')")
    @Transactional
    public ResponseEntity<ShipDTO> updateShip(@RequestBody ShipDTO shipDTO, @PathVariable("id") Long id) {
        LocalDateTime availableFrom = shipDTO.getAvailableFrom();
        LocalDateTime availableTo = shipDTO.getAvailableTo();
        if(availableFrom.isAfter(availableTo))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Reservation> reservations = shipService.findAllReservations(id);
        if(reservations.size() != 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship ship = shipService.findById(shipDTO.getId());
        if (ship == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ship.update(shipDTO);
        ship = shipService.save(ship);
        if (ship == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ShipDTO(ship), HttpStatus.OK);
    }

    @GetMapping(value = "/loggedShipOwner/all")
    @PreAuthorize("hasAnyRole('SHIP_OWNER')")
    @Transactional
    public ResponseEntity<List<ShipDTO>> getAllShipsFromOwner(Principal ownerPrincipal) {
        List<Ship> ships = shipService.findAllFromOwner(ownerPrincipal.getName());
        List<ShipDTO> shipDTOList = getShipDTOList(ships);
        return new ResponseEntity<>(shipDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/loggedShipOwner/{id}/hasReservations")
    @PreAuthorize("hasRole('SHIP_OWNER')")
    public ResponseEntity<Boolean> hasReservations(@PathVariable("id") Long id) {
        List<Reservation> reservations = shipService.findAllReservations(id);
        Boolean hasReservations = reservations.size() != 0;
        return new ResponseEntity<>(hasReservations, HttpStatus.OK);
    }

    @DeleteMapping(value = "/loggedShipOwner/{id}")
    @PreAuthorize("hasRole('SHIP_OWNER')")
//    @Transactional
    public ResponseEntity<ShipDTO> deleteShip(@PathVariable("id") Long id, Principal ownerPrincipal) {
        List<Reservation> reservations = shipService.findAllReservations(id);
        if(reservations.size() != 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship ship = shipService.findById(id);
        String username = ship.getShipOwner().getUsername();
        if(!username.equals(ownerPrincipal.getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        shipService.remove(id);
        return new ResponseEntity<>(new ShipDTO(ship), HttpStatus.OK);
    }
}
