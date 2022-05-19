package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.model.Complaint;

public interface ComplaintService {
    Complaint findById(Long id);
    Complaint save(Complaint complaint);
}
