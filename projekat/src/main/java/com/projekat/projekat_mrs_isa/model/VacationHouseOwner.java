package com.projekat.projekat_mrs_isa.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="v1_vacation_house_owner")
public class VacationHouseOwner extends Client{
    public VacationHouseOwner() {
    }

    public VacationHouseOwner(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
    }
}
