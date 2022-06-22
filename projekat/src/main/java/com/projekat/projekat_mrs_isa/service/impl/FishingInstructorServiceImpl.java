package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.Role;
import com.projekat.projekat_mrs_isa.repository.FishingClassRepository;
import com.projekat.projekat_mrs_isa.repository.FishingInstructorRepository;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.service.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {

    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;

    @Autowired
    private FishingClassRepository fishingClassRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Override
    public FishingInstructor findById(Long id) {
        return fishingInstructorRepository.findById(id).orElse(null);
    }

    @Override
    public List<FishingInstructor> findAll() {
        return fishingInstructorRepository.findAll();
    }

    @Override
    public FishingInstructor save(FishingInstructor fishingInstructor) {
        return fishingInstructorRepository.save(fishingInstructor);
    }

    @Override
    public void remove(Long id) {
        fishingInstructorRepository.deleteById(id);
    }

    @Override
    public FishingInstructor findByEmail(String email) {
        return fishingInstructorRepository.findByEmail(email);
    }

    @Override
    public FishingInstructor addFishingInstructor(Map<String, Object> userMap) {
        List<String> services= new ArrayList<>();
        services.add("Fishing rods");
        FishingInstructor fi = new FishingInstructor(
                (String) userMap.get("email"),
                (String) userMap.get("username"),
                passwordEncoderComponent.encode((String) userMap.get("password")),
                "pictures/user_pictures/0.png",
                (String) userMap.get("firstName"),
                (String) userMap.get("lastName"),
                (String) userMap.get("address"),
                (String) userMap.get("city"),
                (String) userMap.get("country"),
                (String) userMap.get("phoneNum"),services
        );
        List<Role> fishingInstructorRoles = roleRepository.findByName("ROLE_FC_INSTRUCTOR");
        fi.setRoles(fishingInstructorRoles);
        fishingInstructorRepository.save(fi);
        return fi;
    }

    @Override
    public List<UserDTO> findAllDTO() { return fishingInstructorRepository.findAllDTO(); }

    @Override
    public FishingInstructor findByUsername(String username) {
        return fishingInstructorRepository.findByUsername(username);
    }

    @Override
    public List<ReservationDTO> getReservationsFromInstructor(FishingInstructor instructor) {
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        List<FishingClass> fishingClasses = fishingClassRepository.findAllFromInstructor(instructor.getUsername());
        for (FishingClass fishingClass : fishingClasses) {
            List<Reservation> entityReservationList = reservationRepository.findAllFromEntity(fishingClass);
            List<ReservationDTO> entityReservationDTOList = new ArrayList<>();
            for (Reservation r : entityReservationList) {
                ReservationDTO reservationDTO = new ReservationDTO(r);
                if (fishingClass.getPictures().size() > 0) reservationDTO.setImg(fishingClass.getPictures().get(0));
                else reservationDTO.setImg("pictures/renting_entities/0.png");
                entityReservationDTOList.add(reservationDTO);
            }
            reservationDTOList.addAll(entityReservationDTOList);
        }
        reservationDTOList.sort(Comparator.comparing(ReservationDTO::getEnd).reversed());
        return reservationDTOList;
    }

}
