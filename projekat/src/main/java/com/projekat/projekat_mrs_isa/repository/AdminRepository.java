package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Admin;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Complaint;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    @Query("select a from Admin a where a.email= ?1")
    public Admin findByEmail(String email);

    @Query("select new com.projekat.projekat_mrs_isa.dto.ComplaintDTO(c) from Complaint c")
    List<ComplaintDTO> findAllComplaintsDTO();

    @Query("select c from Complaint c where c.id= ?1")
    public Complaint findComplaintById(Long id);
}
