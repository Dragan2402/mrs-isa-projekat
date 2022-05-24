package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.model.Role;
import com.projekat.projekat_mrs_isa.model.ShipOwner;
import com.projekat.projekat_mrs_isa.repository.FishingInstructorRepository;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FishingInstructorService {
    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    public FishingInstructor findById(Long id) {
        return fishingInstructorRepository.findById(id).orElse(null);
    }

    public List<FishingInstructor> findAll() {
        return fishingInstructorRepository.findAll();
    }

    public Page<FishingInstructor> findAll(Pageable page) {
        return fishingInstructorRepository.findAll(page);
    }

    public FishingInstructor save(FishingInstructor fishingInstructor) {
        return fishingInstructorRepository.save(fishingInstructor);
    }

    public void remove(Long id) {
        fishingInstructorRepository.deleteById(id);
    }

    public FishingInstructor findByEmail(String email) {
        return fishingInstructorRepository.findByEmail(email);
    }

    public FishingInstructor addFishingInstructor(Map<String, Object> userMap) {
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
                (String) userMap.get("phoneNum")
        );
        List<Role> fishingInstructorRoles = roleRepository.findByName("ROLE_CLIENT");
        fi.setRoles(fishingInstructorRoles);
        fishingInstructorRepository.save(fi);
        return fi;
    }
}
