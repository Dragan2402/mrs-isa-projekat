package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.RequestDTO;
import com.projekat.projekat_mrs_isa.model.Request;
import java.util.List;

public interface RequestService {
    List<RequestDTO> findAllDTO();
    Request findById(Long id);
    List<Request> findAll();
    Request save(Request request);
    void remove(Long id);

}
