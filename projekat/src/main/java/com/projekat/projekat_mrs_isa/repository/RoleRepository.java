package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findByName(String name);
}
