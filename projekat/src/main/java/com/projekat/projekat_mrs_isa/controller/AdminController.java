package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.*;
import com.projekat.projekat_mrs_isa.model.Complaint;
import com.projekat.projekat_mrs_isa.model.Request;
import com.projekat.projekat_mrs_isa.model.Review;
import com.projekat.projekat_mrs_isa.service.AdminService;
import com.projekat.projekat_mrs_isa.service.ComplaintService;
import com.projekat.projekat_mrs_isa.service.RequestService;
import com.projekat.projekat_mrs_isa.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/admins")
public class AdminController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AdminService adminService;

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
        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        if (request == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        request.setDeleted(true);
        requestService.save(request);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
