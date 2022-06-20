package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.FishingInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FishingInstructorRepository extends JpaRepository<FishingInstructor,Long> {

    @Query("select f from FishingInstructor f where f.email= ?1")
    public FishingInstructor findByEmail(String email);

    @Query(value = "SELECT instr FROM FishingInstructor instr JOIN FETCH instr.additionalServices where instr.id=?1")
    Optional<FishingInstructor> findById(Long id);
}
