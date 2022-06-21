package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.repository.VacationHouseRepository;
import com.projekat.projekat_mrs_isa.service.UtilityService;
import com.projekat.projekat_mrs_isa.service.VacationHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VacationHouseServiceImpl implements VacationHouseService {

    @Autowired
    private VacationHouseRepository vacationHouseRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public VacationHouse findById(Long id) {
        return vacationHouseRepository.findById(id).orElse(null);
    }

    @Autowired
    public UtilityService utilityService;

    @Override
    public List<VacationHouse> findAll() {
        return vacationHouseRepository.findAll();
    }

    @Override
    public List<VacationHouse> findAllFromOwner(String ownerUsername) {
        return vacationHouseRepository.findAllFromOwner(ownerUsername);
    }

    @Override
    public List<VacationHouseDTO> findAllDTO() {
        return vacationHouseRepository.findAllDTO();
    }

    @Override
    public Page<VacationHouse> findAll(Pageable page) {
        return vacationHouseRepository.findAll(page);
    }

    @Override
    public VacationHouse save(VacationHouse vacationHouse) {
        return vacationHouseRepository.save(vacationHouse);
    }

    @Override
    public void remove(Long id) {
        vacationHouseRepository.deleteById(id);
    }

    @Override
    public List<String> findPicturesByVacationHouseId(Long shipId) {
        VacationHouse vacationHouse = vacationHouseRepository.findById(shipId).orElse(null);
        if (vacationHouse != null)
            return vacationHouse.getPictures();
        else
            return new ArrayList<>();
    }

    @Override
    public VacationHouseDTO findDTOById(Long id) {
        VacationHouse vacationHouse = this.findById(id);
        if (vacationHouse == null) {
            return null;
        }
        VacationHouseDTO vacationHouseDTO= new VacationHouseDTO(vacationHouse);
        vacationHouseDTO.setOwnerId(vacationHouse.getVacationHouseOwner().getId());
        return vacationHouseDTO;
    }

    @Override
    public List<VacationHouseDTO> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page) {
        Page<VacationHouse> vacationHouses = vacationHouseRepository.findByCriteria(name,address,startDate,endDate,people,priceMin,priceMax,page);
        List<VacationHouseDTO> vacationHouseDTOS=new ArrayList<>();
        for (VacationHouse vacationHouse : vacationHouses){
            VacationHouseDTO vacationHouseDTO= new VacationHouseDTO(vacationHouse);
            String picturePath="pictures/renting_entities/0.png";
            if(vacationHouse.getPictures().size()>0){
                picturePath=vacationHouse.getPictures().get(0);
            }
            vacationHouseDTO.setImg(utilityService.getPictureEncoded(picturePath));
            vacationHouseDTOS.add(vacationHouseDTO);
        }
        return vacationHouseDTOS;

    }

    @Override
    public List<VacationHouseDTO> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page) {
        Page<VacationHouse> vacationHouses = vacationHouseRepository.findByNoDateCriteria(name,address,people,priceMin,priceMax,page);
        List<VacationHouseDTO> vacationHouseDTOS=new ArrayList<>();
        for (VacationHouse vacationHouse : vacationHouses){
            VacationHouseDTO vacationHouseDTO= new VacationHouseDTO(vacationHouse);
            String picturePath="pictures/renting_entities/0.png";
            if(vacationHouse.getPictures().size()>0){
                picturePath=vacationHouse.getPictures().get(0);
            }
            vacationHouseDTO.setImg(utilityService.getPictureEncoded(picturePath));
            vacationHouseDTOS.add(vacationHouseDTO);
        }
        return vacationHouseDTOS;
      }
      
    public List<Reservation> findAllReservations(Long vacationHouseId) {
        VacationHouse vacationHouse = findById(vacationHouseId);
        if(vacationHouse != null)
            return reservationRepository.getAllFromEntity(vacationHouse);
        else
            return new ArrayList<>();
    }
}
