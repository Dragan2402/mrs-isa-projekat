package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.Complaint;
import com.projekat.projekat_mrs_isa.repository.ComplaintRepository;
import com.projekat.projekat_mrs_isa.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public Complaint findById(Long id) {
        return complaintRepository.findById(id).orElse(null);
    }

    @Override
    public Complaint save(Complaint complaint) {
        return complaintRepository.save(complaint);
    }
}
