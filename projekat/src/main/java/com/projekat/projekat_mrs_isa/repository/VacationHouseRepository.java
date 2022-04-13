package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VacationHouseRepository extends JpaRepository<VacationHouse, Long> {
    @Query("select new com.projekat.projekat_mrs_isa.dto.VacationHouseDTO(vh) from VacationHouse vh")
    public List<VacationHouseDTO> findAllDTO();
}
