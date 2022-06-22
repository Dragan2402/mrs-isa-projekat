package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.model.Complaint;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.ComplaintDTO(c) from Complaint c where c.approved=false")
    List<ComplaintDTO> findAllDTO();

    @Modifying
    @Query("update Complaint r set r.deleted=true where r.rentingEntity=?1")
    void deleteComplaintByRentingEntity(RentingEntity rentingEntity);
}
