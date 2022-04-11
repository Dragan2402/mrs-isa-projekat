package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/offers")
public class OfferController {
    @Autowired
    private OfferService offerService;
}
