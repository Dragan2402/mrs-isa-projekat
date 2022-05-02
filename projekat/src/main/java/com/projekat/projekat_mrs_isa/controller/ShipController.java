package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.model.Ship;
import com.projekat.projekat_mrs_isa.service.ShipService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(value = "/all")
    @Transactional
    public ResponseEntity<List<ShipDTO>> getAllShips() {
        List<ShipDTO> ships = shipService.findAllDTO();
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ShipDTO> getShip(@PathVariable("id") Long id) {
        Ship ship = shipService.findById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ShipDTO(ship), HttpStatus.OK);
    }

    @GetMapping(value = "/{shipId}/pictures/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<String>> getAllPictures(@PathVariable("shipId") Long shipId) {
        List<String> picturePaths = shipService.findPicturesByShipId(shipId);
        List<String> encodedPictures = new ArrayList<>();

        for (String picturePath : picturePaths) {
            Resource r = resourceLoader.getResource("classpath:" + picturePath);
            try {
                File file = r.getFile();
                String encodedPicture = Base64.encodeBase64String(
                        FileUtils.readFileToByteArray(file));
                encodedPictures.add(encodedPicture);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(encodedPictures, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ShipDTO> updateShip(@RequestBody ShipDTO shipDTO) {
        Ship ship = shipService.findById(shipDTO.getId());
        if (ship == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ship.update(shipDTO);
        ship = shipService.save(ship);
        if (ship == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ShipDTO(ship), HttpStatus.OK);
    }
}
