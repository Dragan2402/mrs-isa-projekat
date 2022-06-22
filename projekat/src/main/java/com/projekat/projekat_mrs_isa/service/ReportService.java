package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.ReportDTO;
import com.projekat.projekat_mrs_isa.model.Complaint;
import com.projekat.projekat_mrs_isa.model.Report;

import java.util.List;

public interface ReportService {
    public boolean save(Report report);
    Report findById(Long id);
    List<ReportDTO> findAllDTO();
}
