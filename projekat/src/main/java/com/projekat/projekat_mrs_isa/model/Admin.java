package com.projekat.projekat_mrs_isa.model;


import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;

@Entity
@SQLDelete(sql = "UPDATE admin SET deleted = true WHERE id = ?")
public class Admin extends User{
    public Admin() {
    }

    public Admin(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
    }
}
