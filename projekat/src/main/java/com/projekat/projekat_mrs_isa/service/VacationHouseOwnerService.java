package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.model.Role;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.repository.VacationHouseOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VacationHouseOwnerService {
    @Autowired
    private VacationHouseOwnerRepository vacationHouseOwnerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    public VacationHouseOwner findById(Long id) {
        return vacationHouseOwnerRepository.findById(id).orElse(null);
    }

    public List<VacationHouseOwner> findAll() {
        return vacationHouseOwnerRepository.findAll();
    }

    public VacationHouseOwner save(VacationHouseOwner vacationHouseOwner) {
        return vacationHouseOwnerRepository.save(vacationHouseOwner);
    }

    public void remove(Long id) {
        vacationHouseOwnerRepository.deleteById(id);
    }

    public VacationHouseOwner addVacationHouseOwner(Map<String, Object> userMap) {
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
                (String) userMap.get("phoneNum")
        );
        List<Role> vacationHouseOwnerRoles = roleRepository.findByName("ROLE_CLIENT");
        vho.setRoles(vacationHouseOwnerRoles);
        vacationHouseOwnerRepository.save(vho);
        return vho;
    }
}
