package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.dto.ReviewDisplayDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.service.FishingClassService;
import com.projekat.projekat_mrs_isa.service.FishingInstructorService;
import com.projekat.projekat_mrs_isa.service.RentingEntityService;
import com.projekat.projekat_mrs_isa.service.ReservationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/fishingClasses")
public class FishingClassController {

    @Autowired
    private FishingClassService fishingClassService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private RentingEntityService rentingEntityService;

    @Autowired
    private ReservationService reservationService;


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

    @NotNull
    private List<FishingClassDTO> getFishingClassDTOList(List<FishingClass> fishingClasses) {
        return fishingClassService.convertToDto(fishingClasses);
    }

    @GetMapping(value = "/anyUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<FishingClassDTO> getFishingClass(@PathVariable("id") Long id) {
        FishingClass fishingClass = fishingClassService.findById(id);
        if (fishingClass == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(fishingClassService.findDTOById(id), HttpStatus.OK);
    }




    @GetMapping(value = "/anyUser/{id}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<OfferDTO>> getOffers(@PathVariable("id") Long id) {
        FishingClass fishingClass = fishingClassService.findById(id);
        if (fishingClass == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(rentingEntityService.getOffersByREId(fishingClass),HttpStatus.OK);
    }

    @GetMapping(value = "/anyUser/{id}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<ReviewDisplayDTO>> getReviews(@PathVariable("id") Long id) {
        FishingClass fishingClass = fishingClassService.findById(id);
        if (fishingClass == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(rentingEntityService.getReviewsByRentingEntityIdOrOwnerId(fishingClass.getId(),fishingClass.getFishingInstructor().getId()),HttpStatus.OK);
    }

    @GetMapping(value = "/anyUser/{id}/pictures/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<String>> getAllPictures(@PathVariable("id") Long id) {
        FishingClass fishingClass = fishingClassService.findById(id);
        if (fishingClass == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(rentingEntityService.getPicturesByRentingEntity(fishingClass),HttpStatus.OK);
    }

    @PostMapping(value = "/loggedFishingInstructor/")
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    public ResponseEntity<Boolean> createFishingClass(@RequestBody FishingClassDTO fishingClassDTO, Principal instructorPrincipal) {
        FishingInstructor instructor = fishingInstructorService.findByUsername(instructorPrincipal.getName());
        if(instructor == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        FishingClass fishingClass = new FishingClass(fishingClassDTO);
        instructor.addFishingClass(fishingClass);
        fishingClassService.save(fishingClass);
        fishingInstructorService.save(instructor);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping(value = "/loggedFishingInstructor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<FishingClassDTO> updateFishingClass(@RequestBody FishingClassDTO fishingClassDTO, @PathVariable("id") Long id) {
        LocalDateTime availableFrom = fishingClassDTO.getAvailableFrom();
        LocalDateTime availableTo = fishingClassDTO.getAvailableTo();
        if(availableFrom.isAfter(availableTo)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Reservation> reservations = fishingClassService.findAllReservations(id);
        if(reservations.size() != 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        FishingClass fishingClass = fishingClassService.findById(fishingClassDTO.getId());
        if (fishingClass == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        fishingClass.update(fishingClassDTO);
        fishingClass = fishingClassService.save(fishingClass);
        if (fishingClass == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new FishingClassDTO(fishingClass), HttpStatus.OK);
    }

    @GetMapping(value = "/loggedFishingInstructor/all")
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<List<FishingClassDTO>> getAllFishingClassesFromOwner(Principal instructorPrincipal) {
        List<FishingClass> fishingClasses = fishingClassService.findAllFromInstructor(instructorPrincipal.getName());
        List<FishingClassDTO> fishingClassDTOS = getFishingClassDTOList(fishingClasses);
        return new ResponseEntity<>(fishingClassDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/loggedFishingInstructor/{id}/hasReservations")
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    public ResponseEntity<Boolean> hasReservations(@PathVariable("id") Long id) {
        List<Reservation> reservations = fishingClassService.findAllReservations(id);
        Boolean hasReservations = reservations.size() != 0;
        return new ResponseEntity<>(hasReservations, HttpStatus.OK);
    }

    @DeleteMapping(value = "/loggedFishingInstructor/{id}")
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
//    @Transactional
    public ResponseEntity<FishingClassDTO> deleteFishingClass(@PathVariable("id") Long id, Principal instructorPrincipal) {
        List<Reservation> reservations = fishingClassService.findAllReservations(id);
        if(reservations.size() != 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        FishingClass fishingClass = fishingClassService.findById(id);
        String username = fishingClass.getFishingInstructor().getUsername();
        if(!username.equals(instructorPrincipal.getName())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        fishingClassService.remove(id);
        return new ResponseEntity<>(new FishingClassDTO(fishingClass), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<FishingClassDTO>> getAllFishingClasses() {
        List<FishingClassDTO> fishingClassDTOS = fishingClassService.findAllDTO();
        return new ResponseEntity<>(fishingClassDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> deleteFishingClass(@RequestBody FishingClassDTO fishingClassDTO) {
        FishingClass fishingClass = fishingClassService.findById(fishingClassDTO.getId());
        if (fishingClass == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        rentingEntityService.deleteReviewsByRentingEntity(fishingClass);
        rentingEntityService.deleteComplaintByRentingEntity(fishingClass);
        rentingEntityService.deleteReservationByRentingEntity(fishingClass);
        fishingClass.setDeleted(true);
        fishingClassService.save(fishingClass);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/loggedFishingClassInstructor/getEntitiesRating", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    public ResponseEntity<Double> getEntitiesRating(Principal ownerPrincipal) {
        List<FishingClass> fishingClasses = fishingClassService.findAllFromOwner(ownerPrincipal.getName());
        double rating=0;
        for(FishingClass fishingClass : fishingClasses)
            rating += fishingClass.getRating();
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @GetMapping(value = "/loggedFishingClassInstructor/getMoneyEarned/{startDate}-{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FC_INSTRUCTOR')")
    public ResponseEntity<Double> getEntitiesRating(Principal ownerPrincipal,@PathVariable("startDate") String startDateString,@PathVariable("endDate") String endDateString) {
        LocalDateTime dateStart=LocalDateTime.parse(startDateString, DateTimeFormatter.ofPattern("yyyy_MM_dd HH:mm:ss"));
        LocalDateTime dateEnd=LocalDateTime.parse(endDateString, DateTimeFormatter.ofPattern("yyyy_MM_dd HH:mm:ss"));
        List<FishingClass> fishingClasses = fishingClassService.findAllFromOwner(ownerPrincipal.getName());
        double moneyEarned=0d;
        for(FishingClass fishingClass : fishingClasses){

            for(Reservation reservation : reservationService.getReservationsByDateAndEntity(dateStart,dateEnd,fishingClass)) {
                moneyEarned += reservation.getPrice();
            }
        }
        return new ResponseEntity<>(moneyEarned, HttpStatus.OK);
    }

}
