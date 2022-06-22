package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.ReportDTO;
import com.projekat.projekat_mrs_isa.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.ReportDTO(r) from Report r where r.reviewed=false and r.type=1")
    List<ReportDTO> findAllDTO();
}