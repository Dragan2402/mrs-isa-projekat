package com.projekat.projekat_mrs_isa.model;


import org.hibernate.annotations.SQLDelete;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE ship_owner SET deleted = true WHERE id = ?")
public class ShipOwner extends User {

    @OneToMany(mappedBy = "shipOwner" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<>();

    public ShipOwner() {}

    public ShipOwner(String email, String password, String firstName, String lastName, String address, String city,
                     String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
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
}
