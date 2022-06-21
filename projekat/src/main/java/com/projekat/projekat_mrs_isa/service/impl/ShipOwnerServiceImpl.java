package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Role;
import com.projekat.projekat_mrs_isa.model.ShipOwner;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.repository.ShipOwnerRepository;
import com.projekat.projekat_mrs_isa.service.ShipOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShipOwnerServiceImpl implements ShipOwnerService {

    @Autowired
    private ShipOwnerRepository shipOwnerRepository;

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
    public ShipOwner addShipOwner(Map<String, Object> userMap) {
        List<String> services= new ArrayList<>();
        services.add("Booze");
        ShipOwner so = new ShipOwner(
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

        List<Role> shipOwnerRoles = roleRepository.findByName("ROLE_CLIENT");
        so.setRoles(shipOwnerRoles);
        shipOwnerRepository.save(so);
        return so;
        }

    @Override
    public List<UserDTO> findAllDTO() { return shipOwnerRepository.findAllDTO(); }
}
