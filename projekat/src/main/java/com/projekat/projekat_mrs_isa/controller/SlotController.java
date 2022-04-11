package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/slots")
public class SlotController {
    @Autowired
    private SlotService slotService;
}
