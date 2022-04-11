package com.projekat.projekat_mrs_isa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="v1_vacation_house")
public class VacationHouse extends RentingEntity{

    @Column(name = "roomsQuantity", nullable = false)
    private Integer roomsQuantity;

    @Column(name = "bedsPerRoom", nullable = false)
    private Integer bedsPerRoom;

    public VacationHouse() {
    }

    public VacationHouse(String name, String address, String promoDescription, List<String> pictures, List<Reservation> availableReservations, String behaviourRules, String priceList, String additionalInfo, String cancellationConditions, Integer roomsQuantity, Integer bedsPerRoom) {
        super(name, address, promoDescription, pictures, availableReservations, behaviourRules, priceList, additionalInfo, cancellationConditions);
        this.roomsQuantity = roomsQuantity;
        this.bedsPerRoom = bedsPerRoom;
    }

    public Integer getRoomsQuantity() {
        return roomsQuantity;
    }

    public void setRoomsQuantity(Integer roomsQuantity) {
        this.roomsQuantity = roomsQuantity;
    }

    public Integer getBedsPerRoom() {
        return bedsPerRoom;
    }

    public void setBedsPerRoom(Integer bedsPerRoom) {
        this.bedsPerRoom = bedsPerRoom;
    }

    @Override
    public String toString() {
        return "VacationHouse{" +
                "roomsQuantity=" + roomsQuantity +
                ", bedsPerRoom=" + bedsPerRoom +
                "} " + super.toString();
    }
}
