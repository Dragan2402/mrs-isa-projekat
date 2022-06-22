package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import com.projekat.projekat_mrs_isa.model.VacationHouseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FishingInstructorRepository extends JpaRepository<FishingInstructor,Long> {

    @Query("select f from FishingInstructor f where f.email= ?1")
    public FishingInstructor findByEmail(String email);

    @Query(value = "SELECT instr FROM FishingInstructor instr JOIN FETCH instr.additionalServices where instr.id=?1")
    Optional<FishingInstructor> findById(Long id);

    @Query("select new com.projekat.projekat_mrs_isa.dto.UserDTO(f) from FishingInstructor f")
    public List<UserDTO> findAllDTO();
}
