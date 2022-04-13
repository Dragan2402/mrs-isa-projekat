package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FishingClassRepository extends JpaRepository<FishingClass, Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.FishingClassDTO(f) from FishingClass f")
    public List<FishingClassDTO> findAllDTO();

    @Query("select new com.projekat.projekat_mrs_isa.dto.FishingClassDTO(f) from FishingClass f where f.id = ?1 ")
    public FishingClassDTO findFishingClassDTO(Long id);
}
