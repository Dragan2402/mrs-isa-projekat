package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.repository.OfferRepository;
import com.projekat.projekat_mrs_isa.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public Offer findById(Long id) {
        return offerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    @Override
    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public void remove(Long id) {
        offerRepository.deleteById(id);
    }
}
