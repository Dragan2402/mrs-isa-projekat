package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    public Offer findById(Long id) {
        return offerRepository.findById(id).orElse(null);
    }

    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    public void remove(Long id) {
        offerRepository.deleteById(id);
    }
}
