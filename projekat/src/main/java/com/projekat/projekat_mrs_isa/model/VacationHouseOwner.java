package com.projekat.projekat_mrs_isa.model;


import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Polymorphism(type= PolymorphismType.EXPLICIT)
public class VacationHouseOwner extends User{
    public VacationHouseOwner() {
    }

    public VacationHouseOwner(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
    }
}
