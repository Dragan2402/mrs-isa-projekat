package com.projekat.projekat_mrs_isa.repository;


import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VacationHouseOwnerRepository  extends JpaRepository<VacationHouseOwner,Long> {
    @Query(value = "SELECT vhOwner FROM VacationHouseOwner vhOwner JOIN FETCH vhOwner.additionalServices where vhOwner.id=?1")
    Optional<VacationHouseOwner> findById(Long id);

    @Query("select vho from VacationHouseOwner vho join fetch vho.vacationHouses where vho.username = ?1")
    VacationHouseOwner findByUsername(String username);
}
