package com.projekat.projekat_mrs_isa.model;


import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE vacation_house_owner SET deleted = true WHERE id = ?")
public class VacationHouseOwner extends User{
    public VacationHouseOwner() {
    }

    @Column(name = "additional_services")
    @ElementCollection(targetClass=String.class,fetch = FetchType.LAZY)
    private List<String> additionalServices = new ArrayList<>();

    @OneToMany(mappedBy = "vacationHouseOwner" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<VacationHouse> vacationHouses = new HashSet<>();

    public VacationHouseOwner(String email,String username, String password, String picture,String firstName, String lastName, String address,
                              String city, String country, String phoneNum,List<String> additionalServices) {
        super(email,username, password, picture,firstName, lastName, address, city, country, phoneNum);
        super.setType(AccountType.VH_OWNER);
        this.additionalServices=additionalServices;
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

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void addAditionalService(String addService){
        this.additionalServices.add(addService);
    }
}
