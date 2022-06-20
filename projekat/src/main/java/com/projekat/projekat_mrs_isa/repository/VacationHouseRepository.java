package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VacationHouseRepository extends JpaRepository<VacationHouse, Long> {
    @Query("select new com.projekat.projekat_mrs_isa.dto.VacationHouseDTO(vh) from VacationHouse vh")
    public List<VacationHouseDTO> findAllDTO();

    @Query(value = "SELECT vh FROM VacationHouse vh WHERE vh.name LIKE %?1% and vh.address LIKE %?2% and vh.availableFrom < ?3 and vh.availableTo > ?4 and vh.clientLimit >= ?5 and vh.priceList >= ?6 and vh.priceList <= ?7",
            countQuery = "SELECT count(vh) FROM VacationHouse vh WHERE  vh.name LIKE %?1% and vh.address LIKE %?2% and vh.availableFrom < ?3 and vh.availableTo > ?4 and vh.clientLimit >= ?5 and vh.priceList >= ?6 and vh.priceList <= ?7")
    Page<VacationHouse> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page);

    @Query(value = "SELECT vh FROM VacationHouse vh WHERE vh.name LIKE %?1% and vh.address LIKE %?2% and vh.clientLimit >= ?3 and vh.priceList >= ?4 and vh.priceList <= ?5",
            countQuery = "SELECT count(vh) FROM VacationHouse vh WHERE  vh.name LIKE %?1% and vh.address LIKE %?2% and vh.clientLimit >= ?3 and vh.priceList >= ?4 and vh.priceList <= ?5")
    Page<VacationHouse> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page);

    @Query("select vh from VacationHouse vh where vh.vacationHouseOwner.username = ?1")
    List<VacationHouse> findAllFromOwner(String ownerUsername);

    @Query("select vh from VacationHouse vh join fetch vh.vacationHouseOwner where vh.id = ?1")
    Optional<VacationHouse> findById(Long id);
}

