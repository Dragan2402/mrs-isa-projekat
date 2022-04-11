package com.projekat.projekat_mrs_isa.model;


import javax.persistence.Entity;

@Entity
public class VacationHouseOwner extends User{
    public VacationHouseOwner() {
    }

    public VacationHouseOwner(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
    }
}
