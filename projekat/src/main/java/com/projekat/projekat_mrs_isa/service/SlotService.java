package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;
}
