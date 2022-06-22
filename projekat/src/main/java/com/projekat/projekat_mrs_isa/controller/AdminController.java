package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.*;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/admins")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UtilityService utilityService;
    @Autowired
    private RentingEntityService rentingEntityService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FeeService feeService;

    @Autowired
    private AdminService adminService;
    @Autowired
    private VacationHouseService vacationHouseService;
    @Autowired
    private ShipService shipService;
    @Autowired
    private FishingClassService fishingClassService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ComplaintService complaintService;

    @GetMapping(value = "/complaints/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        List<ComplaintDTO> complaints = complaintService.findAllDTO();
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    @PutMapping(value = "/complaints", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> approveComplaint(@RequestBody ComplaintDTO complaintDTO) {
        Complaint complaint = complaintService.findById(complaintDTO.getId());
        if (complaint == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (complaint.getRentingEntity() == null) {
            User user = userService.findById(complaintDTO.getRespodentId());
            user.recieveComplaint(complaint);
        } else {
            RentingEntity rentingEntity = rentingEntityService.findById(complaintDTO.getRentingEntityId());
            rentingEntity.addComplaint(complaint);
        }
        complaint.setApproved(true);
        complaintService.save(complaint);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping(value = "/complaints/reject", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> rejectComplaint(@RequestBody ComplaintDTO complaintDTO) {
        Complaint complaint = complaintService.findById(complaintDTO.getId());
        if (complaint == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        complaint.setDeleted(true);
        complaintService.save(complaint);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/reviews/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.findAllDTO();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping(value = "/reviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> approveReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.findById(reviewDTO.getId());
        User owner;
        User client = userService.findById(reviewDTO.getClientId());
        if (client == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (review == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (review.getRentingEntity() == null) {
            owner = userService.findById(reviewDTO.getRentingOwnerId());
            owner.addReview(review);
        } else {
            RentingEntity rentingEntity = rentingEntityService.findById(reviewDTO.getRentingEntityId());
            owner = getRentingEntityOwner(rentingEntity.getId());
            rentingEntity.addReview(review);
        }
        review.setApproved(true);
        reviewService.save(review);
        emailService.sendReview(client, owner, review);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping(value = "/reviews/reject", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> rejectReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.findById(reviewDTO.getId());
        if (review == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        review.setDeleted(true);
        reviewService.save(review);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/requests/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RequestDTO>> getAllRequests() {
        List<RequestDTO> requests = requestService.findAllDTO();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PutMapping(value = "/requests", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> approveRequest(@RequestBody RequestDTO requestDTO) {
        Request request = requestService.findById(requestDTO.getId());
        if (request == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = userService.findById(requestDTO.getSubmitterId());
        if (requestDTO.getType() == RequestType.DELETE_ACCOUNT) {
            user.setEnabled(false);
            userService.save(user);
        } else if (requestDTO.getType() == RequestType.BECOME_VH_OWNER || requestDTO.getType() == RequestType.BECOME_SH_OWNER || requestDTO.getType() == RequestType.BECOME_INSTRUCTOR) {
            user.setEnabled(true);
            userService.save(user);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        request.setApproved(true);
        requestService.save(request);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping(value = "/requests/reject", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> rejectRequest(@RequestBody RequestDTO requestDTO) {
        Request request = requestService.findById(requestDTO.getId());
        if (request == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        request.setDeleted(true);
        requestService.save(request);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> changePassword(Principal adminP, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        Admin loggedAdmin = adminService.findByUsername(adminP.getName());
        if (loggedAdmin == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        if (!utilityService.validatePasswords(passwordChangeDTO.getNewPassword(), passwordChangeDTO.getNewPasswordConfirm()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(adminService.updatePassword(loggedAdmin, passwordChangeDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/fee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getFee() {
        Fee fee = feeService.findFee(1L);
        return new ResponseEntity<>(fee.getValue(), HttpStatus.OK);
    }

    @PutMapping(value = "/fee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> setFee(@RequestBody FeeDTO feeDTO) {
        Fee fee = feeService.findFee(1L);
        if (fee == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        fee.setValue(feeDTO.getValue());
        feeService.save(fee);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/reservations/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDTO>> getFilteredReservations() {
        List<ReservationDTO> reservationDTOS = adminService.getPastReservations();
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/loggedAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateLoggedClient(Principal adminP, @RequestBody UserDTO userDTO) {
        if (utilityService.validateName(userDTO.getFirstName()) && utilityService.validateName(userDTO.getLastName())
                && userDTO.getAddress().length() > 2 && userDTO.getCity().length() > 2
                && userDTO.getCountry().length() > 2 && utilityService.validatePhoneNum(userDTO.getPhoneNum())) {
            Admin admin = adminService.findByUsername(adminP.getName());
            admin.update(userDTO);
            Admin updatedAdmin = adminService.save(admin);
            return new ResponseEntity<>(new UserDTO(updatedAdmin), HttpStatus.OK);
        }
        return new ResponseEntity<>(new UserDTO(), HttpStatus.BAD_REQUEST);
    }

    private User getRentingEntityOwner(Long rentingEntityId) {
        RentingEntity rentingEntity = rentingEntityService.findById(rentingEntityId);
        if (rentingEntity.getREType().equals("VH")) {
            return vacationHouseService.findById(rentingEntity.getId()).getVacationHouseOwner();
        } else if (rentingEntity.getREType().equals("FC")) {
            return fishingClassService.findById(rentingEntity.getId()).getFishingInstructor();
        } else {
            return shipService.findById(rentingEntity.getId()).getShipOwner();
        }
    }

}
