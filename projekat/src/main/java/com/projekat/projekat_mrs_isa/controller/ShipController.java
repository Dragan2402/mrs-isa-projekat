package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ShipDTO>> getAllShips() {
        List<ShipDTO> ships = shipService.findAllDTO();
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }
}
