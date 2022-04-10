package com.projekat.projekat_mrs_isa.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "v1_fishing_instructor")
public class FishingInstructor extends  Client{
    public FishingInstructor() {
    }

    public FishingInstructor(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
    }
}
