package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.RequestDTO;
import com.projekat.projekat_mrs_isa.model.Request;
import com.projekat.projekat_mrs_isa.repository.RequestRepository;
import com.projekat.projekat_mrs_isa.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<RequestDTO> findAllDTO() { return requestRepository.findAllDTO(); }

    @Override
    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void remove(Long id) {
        requestRepository.deleteById(id);
    }
}
