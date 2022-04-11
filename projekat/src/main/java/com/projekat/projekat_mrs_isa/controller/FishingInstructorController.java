package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.service.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "api/fishingInstructors")
public class FishingInstructorController {
    @Autowired
    private FishingInstructorService fishingInstructorService;
}
