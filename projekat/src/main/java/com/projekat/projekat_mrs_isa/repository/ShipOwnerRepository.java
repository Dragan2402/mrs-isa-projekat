package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.ShipOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShipOwnerRepository  extends JpaRepository<ShipOwner,Long> {

    @Query(value = "SELECT shOwner FROM ShipOwner shOwner JOIN FETCH shOwner.additionalServices where shOwner.id=?1")
    Optional<ShipOwner> findById(Long id);

    @Query("select so from ShipOwner so where so.username = ?1")
    ShipOwner findByUsername(String username);

    @Query("select new com.projekat.projekat_mrs_isa.dto.UserDTO(s) from ShipOwner s")
    public List<UserDTO> findAllDTO();
}
