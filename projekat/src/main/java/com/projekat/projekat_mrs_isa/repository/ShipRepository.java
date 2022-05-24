package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Ship;
import com.projekat.projekat_mrs_isa.model.ShipOwner;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.ShipDTO(sh) from Ship sh")
    public List<ShipDTO> findAllDTO();

}
