package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.service.VacationHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "api/vacation_houses")
public class VacationHouseController {

    @Autowired
    private VacationHouseService vacationHouseService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<VacationHouseDTO> getVacationHouse(@PathVariable Long id) {
        VacationHouse vacationHouse = vacationHouseService.findById(id);

        if (vacationHouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new VacationHouseDTO(vacationHouse), HttpStatus.OK);
    }
}
