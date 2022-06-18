package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentingEntityRepository extends JpaRepository<RentingEntity, Long> {

    @Query(value = "SELECT re FROM RentingEntity re JOIN FETCH re.subscribers as sub where sub.id=?1")
    List<RentingEntity> getSubscribedRentingEntitiesByClient(Long clientId);

}