package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.model.Report;
import com.projekat.projekat_mrs_isa.repository.ReportRepository;
import com.projekat.projekat_mrs_isa.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public boolean save(Report report) {
        Report r = reportRepository.save(report);
        return r != null;
    }
}
