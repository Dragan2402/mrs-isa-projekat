package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.repository.FishingClassRepository;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.service.FishingClassService;
import com.projekat.projekat_mrs_isa.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FishingClassServiceImpl implements FishingClassService {
    @Autowired
    private FishingClassRepository fishingClassRepository;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public FishingClass findById(Long id) {
        return fishingClassRepository.findById(id).orElse(null);
    }

    @Override
    public FishingClassDTO findFishingClassDTO(Long id) {
        return fishingClassRepository.findFishingClassDTO(id);
    }

    @Override
    public List<FishingClassDTO> findAllDTO() {
        return fishingClassRepository.findAllDTO();
    }

    @Override
    public List<FishingClass> findAll() {
        return fishingClassRepository.findAll();
    }

    @Override
    public FishingClass save(FishingClass fishingClass) {
        return fishingClassRepository.save(fishingClass);
    }

    @Override
    public void remove(Long id) {
        fishingClassRepository.deleteById(id);
    }

    @Override
    public List<String> findPicturesByShipId(Long id) {
        FishingClass fishingClass = fishingClassRepository.findById(id).orElse(null);
        if (fishingClass != null)
            return fishingClass.getPictures();
        else
            return new ArrayList<>();
    }

    @Override
    public FishingClassDTO findDTOById(Long id) {
        FishingClass fishingClass = this.findById(id);
        if (fishingClass == null) {
            return null;
        }
        FishingClassDTO fishingClassDTO = new FishingClassDTO(fishingClass);
        fishingClassDTO.setOwnerId(fishingClass.getFishingInstructor().getId());
        return fishingClassDTO;
    }

    @Override
    public List<FishingClassDTO> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page) {
        Page<FishingClass> fishingClasses = fishingClassRepository.findByCriteria(name, address, startDate, endDate, people, priceMin, priceMax, page);
        List<FishingClassDTO> fishingClassDTOS = new ArrayList<>();
        for (FishingClass fishingClass : fishingClasses) {
            FishingClassDTO fishingClassDTO = new FishingClassDTO(fishingClass);
            String picturePath = "pictures/renting_entities/0.png";
            if (fishingClass.getPictures().size() > 0) {
                picturePath = fishingClass.getPictures().get(0);
            }
            fishingClassDTO.setImg(utilityService.getPictureEncoded(picturePath));
            fishingClassDTOS.add(fishingClassDTO);
        }
        return fishingClassDTOS;
    }

    @Override
    public List<FishingClassDTO> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page) {
        Page<FishingClass> fishingClasses = fishingClassRepository.findByNoDateCriteria(name, address, people, priceMin, priceMax, page);
        List<FishingClassDTO> fishingClassDTOS = new ArrayList<>();
        for (FishingClass fishingClass : fishingClasses) {
            FishingClassDTO fishingClassDTO = new FishingClassDTO(fishingClass);
            String picturePath = "pictures/renting_entities/0.png";
            if (fishingClass.getPictures().size() > 0) {
                picturePath = fishingClass.getPictures().get(0);
            }
            fishingClassDTO.setImg(utilityService.getPictureEncoded(picturePath));
            fishingClassDTOS.add(fishingClassDTO);
        }
        return fishingClassDTOS;
    }

    @Override
    public List<FishingClassDTO> convertToDto(List<FishingClass> fishingClasses) {
        List<FishingClassDTO> fishingClassDTOS=new ArrayList<>();
        for (FishingClass fishingClass : fishingClasses){
            FishingClassDTO fishingClassDTO= new FishingClassDTO(fishingClass);
            String picturePath="pictures/renting_entities/0.png";
            if(fishingClass.getPictures().size()>0){
                picturePath=fishingClass.getPictures().get(0);
            }
            fishingClassDTO.setImg(utilityService.getPictureEncoded(picturePath));
            fishingClassDTOS.add(fishingClassDTO);
        }
        return fishingClassDTOS;
    }

    @Override
    public List<Reservation> findAllReservations(Long id) {
        FishingClass fishingClass = findById(id);
        if(fishingClass != null) return reservationRepository.findAllFromEntity(fishingClass);
        else return new ArrayList<>();
    }

    @Override
    public List<FishingClass> findAllFromInstructor(String name) {
        return fishingClassRepository.findAllFromInstructor(name);
    }
}
