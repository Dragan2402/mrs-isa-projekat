package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.RequestDTO;
import com.projekat.projekat_mrs_isa.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.RequestDTO(r) from Request r where r.approved=false")
    List<RequestDTO> findAllDTO();

    @Modifying
    @Query("update Request r set r.deleted=true where r.submitter=?1")
    void deleteRequestBySubmitter(Request request);
}
