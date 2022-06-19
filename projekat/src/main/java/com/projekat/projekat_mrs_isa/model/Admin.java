package com.projekat.projekat_mrs_isa.model;


import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;

@Entity
@SQLDelete(sql = "UPDATE admin SET deleted = true WHERE id = ?")
public class Admin extends User{
    public Admin() {}

    public Admin(String email,String username, String password,String picture, String firstName, String lastName, String address, String city,
                 String country, String phoneNum) {
        super(email, username,password,picture, firstName, lastName, address, city, country, phoneNum);
        super.setType(AccountType.ADMIN);
    }
}
