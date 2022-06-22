package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.Role;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.repository.VacationHouseOwnerRepository;
import com.projekat.projekat_mrs_isa.repository.VacationHouseRepository;
import com.projekat.projekat_mrs_isa.service.VacationHouseOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class VacationHouseOwnerServiceImpl implements VacationHouseOwnerService {
    @Autowired
    private VacationHouseOwnerRepository vacationHouseOwnerRepository;

    @Autowired
    private VacationHouseRepository vacationHouseRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Override
    public VacationHouseOwner findById(Long id) {
        return vacationHouseOwnerRepository.findById(id).orElse(null);
    }

    @Override
    public VacationHouseOwner findByUsername(String username) {
        return vacationHouseOwnerRepository.findByUsername(username);
    }

    @Override
    public List<VacationHouseOwner> findAll() {
        return vacationHouseOwnerRepository.findAll();
    }

    @Override
    public VacationHouseOwner save(VacationHouseOwner vacationHouseOwner) {
        return vacationHouseOwnerRepository.save(vacationHouseOwner);
    }

    @Override
    public void remove(Long id) {
        vacationHouseOwnerRepository.deleteById(id);
    }

    @Override
    public List<ReservationDTO> getReservationsFromOwner(VacationHouseOwner owner) {
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        List<VacationHouse> vacationHouses = vacationHouseRepository.findAllFromOwner(owner.getUsername());
        for (VacationHouse vh : vacationHouses) {
            List<Reservation> entityReservationList = reservationRepository.findAllFromEntity(vh);
            List<ReservationDTO> entityReservationDTOList = new ArrayList<>();
            for (Reservation r : entityReservationList) {
                ReservationDTO reservationDTO = new ReservationDTO(r);
                if (vh.getPictures().size() > 0)
                    reservationDTO.setImg(vh.getPictures().get(0));
                else
                    reservationDTO.setImg("pictures/renting_entities/0.png");
                entityReservationDTOList.add(reservationDTO);
            }
            reservationDTOList.addAll(entityReservationDTOList);
        }
        reservationDTOList.sort(Comparator.comparing(ReservationDTO::getEnd).reversed());
        return reservationDTOList;
    }

    public VacationHouseOwner addVacationHouseOwner(Map<String, Object> userMap) {
        List<String> services= new ArrayList<>();
        services.add("Grill");
        VacationHouseOwner vho = new VacationHouseOwner(
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
        List<Role> vacationHouseOwnerRoles = roleRepository.findByName("ROLE_VH_OWNER");
        vho.setRoles(vacationHouseOwnerRoles);
        vacationHouseOwnerRepository.save(vho);
        return vho;
    }

    @Override
    public List<UserDTO> findAllDTO() { return vacationHouseOwnerRepository.findAllDTO(); }
}
