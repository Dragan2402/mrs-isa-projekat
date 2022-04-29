package com.projekat.projekat_mrs_isa.model;


import org.hibernate.annotations.SQLDelete;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE vacation_house_owner SET deleted = true WHERE id = ?")
public class VacationHouseOwner extends User{
    public VacationHouseOwner() {
    }

    @OneToMany(mappedBy = "vacationHouseOwner" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<VacationHouse> vacationHouses = new HashSet<>();

    public VacationHouseOwner(String email, String password, String picture,String firstName, String lastName, String address,
                              String city, String country, String phoneNum) {
        super(email, password, picture,firstName, lastName, address, city, country, phoneNum);
    }

    public Set<VacationHouse> getVacationHouses() {
        return vacationHouses;
    }

    public void setVacationHouses(Set<VacationHouse> vacationHouses) {
        this.vacationHouses = vacationHouses;
    }

    public void addVacationHouse(VacationHouse vacationHouse){
        vacationHouses.add(vacationHouse);
        vacationHouse.setVacationHouseOwner(this);
    }

    public void removeVacationHouse(VacationHouse vacationHouse){
        vacationHouses.remove(vacationHouse);
        vacationHouse.setVacationHouseOwner(null);
    }
}
