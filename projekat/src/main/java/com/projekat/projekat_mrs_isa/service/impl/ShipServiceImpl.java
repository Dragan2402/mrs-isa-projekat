package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Ship;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.repository.ShipRepository;
import com.projekat.projekat_mrs_isa.service.ShipService;
import com.projekat.projekat_mrs_isa.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private UtilityService utilityService;

    @Override
    public Ship findById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ship> findAll() {
        return shipRepository.findAll();
    }

    @Override
    public Ship save(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public List<ShipDTO> findAllDTO() { return shipRepository.findAllDTO(); }

    @Override
    public void remove(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public List<String> findPicturesByShipId(Long shipId) {
        Ship ship = shipRepository.findById(shipId).orElse(null);
        if (ship != null)
            return ship.getPictures();
        return new ArrayList<>();
    }

    @Override
    public ShipDTO findDTOById(Long id) {
        Ship ship = this.findById(id);
        if (ship == null) {
            return null;
        }
        ShipDTO shipDTO= new ShipDTO(ship);
        shipDTO.setOwnerId(ship.getShipOwner().getId());
        return shipDTO;
    }

    @Override
    public List<ShipDTO> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page) {
        Page<Ship> ships=shipRepository.findByCriteria(name,address,startDate,endDate,people,priceMin,priceMax,page);
        List<ShipDTO> shipsDTOS=new ArrayList<>();
        for (Ship ship : ships){
            ShipDTO shipDTO= new ShipDTO(ship);
            String picturePath="pictures/renting_entities/0.png";
            if(ship.getPictures().size()>0){
                picturePath=ship.getPictures().get(0);
            }
            shipDTO.setImg(utilityService.getPictureEncoded(picturePath));
            shipsDTOS.add(shipDTO);
        }
        return shipsDTOS;
    }

    @Override
    public List<ShipDTO> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page) {
        Page<Ship> ships= shipRepository.findByNoDateCriteria(name,address,people,priceMin,priceMax,page);
        List<ShipDTO> shipsDTOS=new ArrayList<>();
        for (Ship ship : ships){
            ShipDTO shipDTO= new ShipDTO(ship);
            String picturePath="pictures/renting_entities/0.png";
            if(ship.getPictures().size()>0){
                picturePath=ship.getPictures().get(0);
            }
            shipDTO.setImg(utilityService.getPictureEncoded(picturePath));
            shipsDTOS.add(shipDTO);
        }
        return shipsDTOS;
    }
}
