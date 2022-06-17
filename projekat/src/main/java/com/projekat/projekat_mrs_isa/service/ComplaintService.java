package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.model.Complaint;

import java.util.List;

public interface ComplaintService {

    List<ComplaintDTO> findAllDTO();
    Complaint findById(Long id);
    Complaint save(Complaint complaint);
}
