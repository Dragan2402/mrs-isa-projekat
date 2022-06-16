package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Complaint;
import com.projekat.projekat_mrs_isa.service.AdminService;
import com.projekat.projekat_mrs_isa.service.ComplaintService;
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
    private AdminService adminService;

    @Autowired
    private ComplaintService complaintService;

    @GetMapping(value = "/complaints/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        List<ComplaintDTO> complaints = adminService.findAllComplaintsDTO();
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    @PutMapping(value = "/complaints", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> approveComplaint(@RequestBody ComplaintDTO complaintDTO) {
        Complaint complaint = adminService.findComplaintById(complaintDTO.getId());
        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        complaint.setApproved(true);
        complaintService.save(complaint);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
