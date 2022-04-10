package com.projekat.projekat_mrs_isa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/hello")
public class HelloWorld {

    @GetMapping(value = "hello")
    public  String Hello(){
        return "HELLO";
    }
}
