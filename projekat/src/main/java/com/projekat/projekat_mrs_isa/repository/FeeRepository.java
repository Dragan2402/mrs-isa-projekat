package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeeRepository extends JpaRepository<Fee, Long> {

    @Query("select f from Fee f where f.id= ?1")
    public Fee findFee(Long id);
}
