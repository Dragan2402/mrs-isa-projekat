package com.projekat.projekat_mrs_isa.model;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class FishingInstructor extends  User{
    public FishingInstructor() {
    }

    public FishingInstructor(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
    }
}
