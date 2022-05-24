package com.projekat.projekat_mrs_isa.service;


import com.projekat.projekat_mrs_isa.model.Role;
import java.util.List;

public interface RoleService {

    Role findById(Long id);
    List<Role> findByName(String name);

}
