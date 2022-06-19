package com.projekat.projekat_mrs_isa.model;


import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE ship_owner SET deleted = true WHERE id = ?")
public class ShipOwner extends User {

    @OneToMany(mappedBy = "shipOwner" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<>();

    @Column(name = "additional_services")
    @ElementCollection(targetClass=String.class,fetch = FetchType.LAZY)
    private List<String> additionalServices = new ArrayList<>();

    public ShipOwner() {}

    public ShipOwner(String email,String username, String password,String picture ,String firstName, String lastName, String address, String city,
                     String country, String phoneNum,List<String> additionalServices) {
        super(email, username,password, picture,firstName, lastName, address, city, country, phoneNum);
        super.setType(AccountType.SH_OWNER);
        this.additionalServices=additionalServices;
        this.additionalServices.add("Book a Captain");
        this.additionalServices.add("Book a First Officer");
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public void addShip(Ship ship){
        ships.add(ship);
        ship.setShipOwner(this);
    }

    public void removeShip(Ship ship){
        ships.remove(ship);
        ship.setShipOwner(null);
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
