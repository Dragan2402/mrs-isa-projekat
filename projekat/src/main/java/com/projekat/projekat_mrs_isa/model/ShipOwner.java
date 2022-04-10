package com.projekat.projekat_mrs_isa.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="v1_ship_owner")
public class ShipOwner extends Client {
    public ShipOwner() {
    }

    public ShipOwner(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
    }
}
