package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.service.VacationHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "api/vacationHouses")
public class VacationHouseController {
    @Autowired
    private VacationHouseService vacationHouseService;
}
