package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import com.projekat.projekat_mrs_isa.model.FishingClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FishingClassRepository extends JpaRepository<FishingClass, Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.FishingClassDTO(f) from FishingClass f")
    public List<FishingClassDTO> findAllDTO();

    @Query("select new com.projekat.projekat_mrs_isa.dto.FishingClassDTO(f) from FishingClass f where f.id = ?1 ")
    public FishingClassDTO findFishingClassDTO(Long id);

    @Query(value = "SELECT fc FROM FishingClass fc WHERE fc.name LIKE %?1% and fc.address LIKE %?2% and fc.availableFrom < ?3 and fc.availableTo > ?4 and fc.clientLimit >= ?5 and fc.priceList >= ?6 and fc.priceList <= ?7",
            countQuery = "SELECT count(fc) FROM FishingClass fc WHERE  fc.name LIKE %?1% and fc.address LIKE %?2% and fc.availableFrom < ?3 and fc.availableTo > ?4 and fc.clientLimit >= ?5 and fc.priceList >= ?6 and fc.priceList <= ?7")
    Page<FishingClass> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page);

    @Query(value = "SELECT fc FROM FishingClass fc WHERE fc.name LIKE %?1% and fc.address LIKE %?2% and fc.clientLimit >= ?3 and fc.priceList >= ?4 and fc.priceList <= ?5",
            countQuery = "SELECT count(fc) FROM FishingClass fc WHERE  fc.name LIKE %?1% and fc.address LIKE %?2% and fc.clientLimit >= ?3 and fc.priceList >= ?4 and fc.priceList <= ?5")
    Page<FishingClass> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page);

    @Query("select distinct fc from FishingClass fc left join fetch fc.pictures where fc.fishingInstructor.username = ?1")
    List<FishingClass> findAllFromOwner(String username);
    
    @Query("select distinct fc from FishingClass fc left join fetch fc.pictures where fc.fishingInstructor.username = ?1")
    List<FishingClass> findAllFromInstructor(String username);
}
