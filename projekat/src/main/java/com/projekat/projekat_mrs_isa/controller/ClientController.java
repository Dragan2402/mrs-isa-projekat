package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.*;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RentingEntityService rentingEntityService;



    @Autowired
    private UtilityService utilityService;



    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> getAllClients() {
        List<UserDTO> clients = clientService.findAllDTO();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    public ResponseEntity<UserDTO> getClientDTOById(@PathVariable("id") Long id) {
        UserDTO clientDTO = clientService.findUserDTO(id);
        if (clientDTO == null)
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<UserDTO>(clientDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/addReviewRE", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> addReviewRE(Principal clientP, @RequestBody ReviewDTO reviewDTO) {
        RentingEntity rentingEntity = rentingEntityService.findById(reviewDTO.getRentingEntityId());
        if (rentingEntity == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Review review = new Review(rentingEntity, client, reviewDTO.getRating(), reviewDTO.getComment());
        //rentingEntity.addReview(review);
        //client.addReview(review);
        Review saved = reviewService.save(review);
        if (saved == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(value = "/addComplaintRE", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> addComplaintRE(Principal clientP, @RequestBody ComplaintDTO complaintDTO) {
        RentingEntity rentingEntity = rentingEntityService.findById(complaintDTO.getRentingEntityId());
        if (rentingEntity == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Complaint complaint=new Complaint(client,rentingEntity,complaintDTO.getText());
        //rentingEntity.addComplaint(complaint);
        //client.sendComplaint(complaint);
        Complaint complaintSaved = complaintService.save(complaint);
        if (complaintSaved == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(value = "/addComplaintRO", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> addComplaintRO(Principal clientP, @RequestBody ComplaintDTO complaintDTO) {
        User respodent = userService.findById(complaintDTO.getRespodentId());
        if (respodent == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Complaint complaint=new Complaint(client,respodent,complaintDTO.getText());
        //respodent.recieveComplaint(complaint);
        //client.sendComplaint(complaint);
        Complaint complaintSaved = complaintService.save(complaint);
        if (complaintSaved == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(value = "/addReviewRO", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> addReviewRO(Principal clientP, @RequestBody ReviewDTO reviewDTO) {

        User user = userService.findById(reviewDTO.getRentingOwnerId());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Review review = new Review(user, client, reviewDTO.getRating(), reviewDTO.getComment());
        //user.addReview(review);
        //client.addReview(review);
        Review saved = reviewService.save(review);
        if (saved == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(value = "/makeReservationFull", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> makeReservationFull(Principal clientP, @RequestBody ReservationRequestDTO reservationRequestDTO) {
        Client logged = clientService.findByUsername(clientP.getName());
        if (logged == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            return new ResponseEntity<>(clientService.makeClientReservation(logged,reservationRequestDTO),HttpStatus.OK);
        } catch (ObjectOptimisticLockingFailureException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.LOCKED);
        }
    }


    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> changePassword(Principal clientP, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        Client logged = clientService.findByUsername(clientP.getName());
        if (logged == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        if(!utilityService.validatePasswords(passwordChangeDTO.getNewPassword(),passwordChangeDTO.getNewPasswordConfirm()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(clientService.updatePassword(logged,passwordChangeDTO),HttpStatus.OK);
    }


    @GetMapping(value = "/loggedClient", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<UserDTO> getLoggedClient(Principal clientP) throws Exception {
        Client logged = clientService.findByUsername(clientP.getName());
        if (logged == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserDTO clientDTO = new UserDTO(logged);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }


    @GetMapping(value = "/loggedClient/reservationHistory", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> getLoggedClientReservationHistory(Principal clientP) throws Exception {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientService.getClientReservationHistory(client), HttpStatus.OK);
    }

    @GetMapping(value = "/loggedClient/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> getLoggedClientReservations(Principal clientP) throws Exception {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientService.getClientReservations(client), HttpStatus.OK);
    }

    @PutMapping(value = "/cancelReservation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> cancelReservation(Principal clientP, @RequestBody ReservationDTO reservationDTO) {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            return new ResponseEntity<>(reservationService.cancelReservation(reservationDTO.getId()), HttpStatus.OK);
        }
        catch (ObjectOptimisticLockingFailureException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.LOCKED);
        }
    }

    @PutMapping(value = "/subscribe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> subscribe(Principal clientP, @RequestBody RentingEntityDTO rentingEntityDTO) {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientService.subscribe(client,rentingEntityDTO.getId()), HttpStatus.OK);
    }

    @PutMapping(value = "/unSubscribe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> unSubscribe(Principal clientP, @RequestBody RentingEntityDTO rentingEntityDTO) {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientService.unSubscribe(client,rentingEntityDTO.getId()), HttpStatus.OK);
    }

    @PutMapping(value = "/unSubscribeDTO", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> unSubscribe(Principal clientP, @RequestBody SubscriptionDTO subscriptionDTO) {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientService.unSubscribe(client,subscriptionDTO.getId()), HttpStatus.OK);
    }

    @GetMapping(value = "/isSubscribed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<Boolean> isSubscribed(Principal clientP,@PathVariable("id") Long id) {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientService.isSubscribed(client,id), HttpStatus.OK);
    }

    @GetMapping(value = "/rentingEntityAvailability/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<List<TakenPeriodDTO>> rentingEntityAvailability(Principal clientP,@PathVariable("id") Long id) {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientService.rentingEntityAvailability(client,id), HttpStatus.OK);
    }


    @GetMapping(value = "/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<List<SubscriptionDTO>> isSubscribed(Principal clientP) {
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientService.getSubscriptions(client),HttpStatus.OK);
    }


    @PutMapping(value = "/loggedClient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<UserDTO> updateLoggedClient(Principal clientP, @RequestBody UserDTO userDTO) throws Exception {

        if (utilityService.validateName(userDTO.getFirstName()) && utilityService.validateName(userDTO.getLastName())
                && userDTO.getAddress().length() > 2 &&
                userDTO.getCity().length() > 2 && userDTO.getCountry().length() > 2 && utilityService.validatePhoneNum(userDTO.getPhoneNum())) {
            Client clientToUpdate = clientService.findByUsername(clientP.getName());
            clientToUpdate.update(userDTO);
            Client updatedCLient = clientService.save(clientToUpdate);
            if (updatedCLient == null)
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(new UserDTO(updatedCLient), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new UserDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/loggedOwner/sendReservationReport", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('VH_OWNER', 'SHIP_OWNER', 'FC_INSTRUCTOR')")
    public ResponseEntity<Boolean> createReservationReport(Principal userPrincipal, @RequestBody ReportDTO reportDTO) {
        User user = userService.findByUsername(userPrincipal.getName());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Reservation reservation = reservationService.findById(reportDTO.getReservationId());
        Client client = clientService.findById(reportDTO.getClientId());
        if (reservation == null || client == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Report report = new Report(reportDTO.getText(), reportDTO.getType(), reservation, client, user);
        if (report.getType() == ReportType.NO_SHOW) {
            client.addPenalty();
            report.setReviewed(true);
            clientService.save(client);
        } else if (report.getType() == ReportType.GOOD)
            report.setReviewed(true);
        reservation.setReviewed(true);
        reservationService.save(reservation);
        return new ResponseEntity<>(reportService.save(report), HttpStatus.OK);
    }
}
