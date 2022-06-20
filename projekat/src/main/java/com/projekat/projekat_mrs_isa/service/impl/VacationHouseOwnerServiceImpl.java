package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.model.Role;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.repository.VacationHouseOwnerRepository;
import com.projekat.projekat_mrs_isa.service.VacationHouseOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VacationHouseOwnerServiceImpl implements VacationHouseOwnerService {
    @Autowired
    private VacationHouseOwnerRepository vacationHouseOwnerRepository;

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

    public VacationHouseOwner addVacationHouseOwner(Map<String, Object> userMap) {
        List<String> services= new ArrayList<>();
        services.add("Grill");
        VacationHouseOwner vho = new VacationHouseOwner(
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
        List<Role> vacationHouseOwnerRoles = roleRepository.findByName("ROLE_CLIENT");
        vho.setRoles(vacationHouseOwnerRoles);
        vacationHouseOwnerRepository.save(vho);
        return vho;
    }
}
