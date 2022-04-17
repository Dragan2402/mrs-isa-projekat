package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.ShipDTO(sh) from Ship sh")
    public List<ShipDTO> findAllDTO();
}
