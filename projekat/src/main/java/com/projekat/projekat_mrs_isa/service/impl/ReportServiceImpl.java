package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.ReportDTO;
import com.projekat.projekat_mrs_isa.model.Report;
import com.projekat.projekat_mrs_isa.repository.ReportRepository;
import com.projekat.projekat_mrs_isa.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<ReportDTO> findAllDTO() { return reportRepository.findAllDTO(); }

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public boolean save(Report report) {
        Report r = reportRepository.save(report);
        return r != null;
    }


}
