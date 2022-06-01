package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VacationHouseRepository extends JpaRepository<VacationHouse, Long> {
    @Query("select new com.projekat.projekat_mrs_isa.dto.VacationHouseDTO(vh) from VacationHouse vh")
    List<VacationHouseDTO> findAllDTO();

    @Query("select vh from VacationHouse vh where vh.vacationHouseOwner.username = ?1")
    List<VacationHouse> findAllFromOwner(String ownerUsername);

    @Query("select vh from VacationHouse vh join fetch vh.vacationHouseOwner where vh.id = ?1")
    Optional<VacationHouse> findById(Long id);
}
