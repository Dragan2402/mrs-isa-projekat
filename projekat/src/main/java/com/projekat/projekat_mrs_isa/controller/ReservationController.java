package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
}