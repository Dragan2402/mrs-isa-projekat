package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Offer;

import java.util.List;


public interface OfferService {

    Offer findById(Long id);

    List<Offer> findAll();

    Offer save(Offer offer);

    void remove(Long id);
}
