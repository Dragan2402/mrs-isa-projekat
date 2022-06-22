package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Role;
import com.projekat.projekat_mrs_isa.model.ShipOwner;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.repository.ShipOwnerRepository;
import com.projekat.projekat_mrs_isa.repository.ShipRepository;
import com.projekat.projekat_mrs_isa.service.ShipOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class ShipOwnerServiceImpl implements ShipOwnerService {

    @Autowired
    private ShipOwnerRepository shipOwnerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    public ShipOwner findById(Long id) {
        return shipOwnerRepository.findById(id).orElse(null);
    }

    @Override
    public ShipOwner findByUsername(String username) {
        return shipOwnerRepository.findByUsername(username);
    }

    @Override
    public void remove(Long id) {
            shipOwnerRepository.deleteById(id);
        }

    @Override
    public List<ShipOwner> findAll() {
        return shipOwnerRepository.findAll();
    }

    @Override
    public ShipOwner save(ShipOwner shipOwner) {
        return shipOwnerRepository.save(shipOwner);
    }

    @Override
    public List<ReservationDTO> getReservationsFromOwner(ShipOwner owner) {
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        List<Ship> ships = shipRepository.findAllFromOwner(owner.getUsername());
        for (Ship s : ships) {
            List<Reservation> entityReservationList = reservationRepository.findAllFromEntity(s);
            List<ReservationDTO> entityReservationDTOList = new ArrayList<>();
            for (Reservation r : entityReservationList) {
                ReservationDTO reservationDTO = new ReservationDTO(r);
                if (s.getPictures().size() > 0)
                    reservationDTO.setImg(s.getPictures().get(0));
                else
                    reservationDTO.setImg("src/main/resources/pictures/renting_entities/0.png");
                entityReservationDTOList.add(reservationDTO);
            }
            reservationDTOList.addAll(entityReservationDTOList);
        }
        reservationDTOList.sort(Comparator.comparing(ReservationDTO::getEnd).reversed());
        return reservationDTOList;
    }

    @Override
    public ShipOwner addShipOwner(Map<String, Object> userMap) {
        List<String> services= new ArrayList<>();
        services.add("Booze");
        ShipOwner so = new ShipOwner(
                    (String) userMap.get("email"),
                    (String) userMap.get("username"),
                    passwordEncoderComponent.encode((String) userMap.get("password")),
                    "src/main/resources/pictures/user_pictures/0.png",
                    (String) userMap.get("firstName"),
                    (String) userMap.get("lastName"),
                    (String) userMap.get("address"),
                    (String) userMap.get("city"),
                    (String) userMap.get("country"),
                    (String) userMap.get("phoneNum"),services
                    );

        List<Role> shipOwnerRoles = roleRepository.findByName("ROLE_CLIENT");
        so.setRoles(shipOwnerRoles);
        shipOwnerRepository.save(so);
        return so;
        }

    @Override
    public List<UserDTO> findAllDTO() { return shipOwnerRepository.findAllDTO(); }
}
