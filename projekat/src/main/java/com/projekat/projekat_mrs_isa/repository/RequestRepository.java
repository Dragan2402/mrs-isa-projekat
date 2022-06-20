package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.RequestDTO;
import com.projekat.projekat_mrs_isa.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.RequestDTO(r) from Request r")
    List<RequestDTO> findAllDTO();
}
