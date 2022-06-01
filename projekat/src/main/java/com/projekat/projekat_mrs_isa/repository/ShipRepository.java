package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import com.projekat.projekat_mrs_isa.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.ShipDTO(sh) from Ship sh")
    public List<ShipDTO> findAllDTO();

    @Query(value = "SELECT sh FROM Ship sh WHERE sh.name LIKE %?1% and sh.address LIKE %?2% and sh.availableFrom < ?3 and sh.availableTo > ?4 and sh.clientLimit >= ?5 and sh.priceList >= ?6 and sh.priceList <= ?7",
            countQuery = "SELECT count(sh) FROM Ship sh WHERE  sh.name LIKE %?1% and sh.address LIKE %?2% and sh.availableFrom < ?3 and sh.availableTo > ?4 and sh.clientLimit >= ?5 and sh.priceList >= ?6 and sh.priceList <= ?7")
    Page<Ship> findByCriteria(String name, String address, LocalDateTime startDate, LocalDateTime endDate, Integer people, Double priceMin, Double priceMax, Pageable page);

    @Query(value = "SELECT sh FROM Ship sh WHERE sh.name LIKE %?1% and sh.address LIKE %?2% and sh.clientLimit >= ?3 and sh.priceList >= ?4 and sh.priceList <= ?5",
            countQuery = "SELECT count(sh) FROM Ship sh WHERE  sh.name LIKE %?1% and sh.address LIKE %?2% and sh.clientLimit >= ?3 and sh.priceList >= ?4 and sh.priceList <= ?5")
    Page<Ship> findByNoDateCriteria(String name, String address, Integer people, Double priceMin, Double priceMax, Pageable page);

    @Query("select s from Ship s where s.shipOwner.username = ?1")
    List<Ship> findAllFromOwner(String ownerUsername);
}
