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
        if (review == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (review.getRentingEntity() == null) {
            User user = userService.findById(reviewDTO.getRentingOwnerId());
            user.addReview(review);
        } else {
            RentingEntity rentingEntity = rentingEntityService.findById(reviewDTO.getRentingEntityId());
            rentingEntity.addReview(review);
        }
        review.setApproved(true);
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
            user.setDeleted(true);
            userService.save(user);
        } else if (requestDTO.getType() == RequestType.BECOME_VH_OWNER || requestDTO.getType() == RequestType.BECOME_SH_OWNER || requestDTO.getType() == RequestType.BECOME_INSTRUCTOR) {
            user.setEnabled(true);
            userService.save(user);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservationDTOS = reservationService.findAllDTO();
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }
}
