package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.VacationHouseDTO;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE vacation_house SET deleted = true WHERE id = ?")
public class VacationHouse extends RentingEntity {

    @Column(name = "rooms_quantity", nullable = false)
    private Integer roomsQuantity;

    @Column(name = "beds_per_room", nullable = false)
    private Integer bedsPerRoom;

    @Column(name = "client_limit", nullable = false)
    private Integer clientLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacation_house_owner_id",nullable = false)
    private VacationHouseOwner vacationHouseOwner;

    public VacationHouse() {}



    public VacationHouse(String name, String address, String promoDescription, List<String> pictures,
                         String behaviourRules, Double priceList, String additionalInfo, String cancellationConditions,
                         Integer roomsQuantity, Integer bedsPerRoom) {
        super(name, address, promoDescription, pictures, behaviourRules, priceList, additionalInfo, cancellationConditions);
        this.roomsQuantity = roomsQuantity;
        this.bedsPerRoom = bedsPerRoom;
        this.clientLimit=roomsQuantity*bedsPerRoom;
    }

    public VacationHouse(VacationHouseDTO vacationHouseDTO) {
        super(vacationHouseDTO);
        this.roomsQuantity = vacationHouseDTO.getRoomsQuantity();
        this.bedsPerRoom = vacationHouseDTO.getBedsPerRoom();
        this.clientLimit=roomsQuantity*bedsPerRoom;
    }

    public Integer getRoomsQuantity() {
        return roomsQuantity;
    }

    public void setRoomsQuantity(Integer roomsQuantity) {
        this.roomsQuantity = roomsQuantity;
        this.clientLimit=roomsQuantity*bedsPerRoom;
    }

    public Integer getBedsPerRoom() {
        return bedsPerRoom;
    }

    public void setBedsPerRoom(Integer bedsPerRoom) {
        this.bedsPerRoom = bedsPerRoom;
        this.clientLimit=roomsQuantity*bedsPerRoom;
    }

    public Integer getClientLimit() {
        return clientLimit;
    }

    public void setClientLimit(Integer clientLimit) {
        this.clientLimit = clientLimit;
    }

    public VacationHouseOwner getVacationHouseOwner() {
        return vacationHouseOwner;
    }

    public void setVacationHouseOwner(VacationHouseOwner vacationHouseOwner) {
        this.vacationHouseOwner = vacationHouseOwner;
    }
    @Override
    public String getREType() {
        return "VH";
    }

    @Override
    public String toString() {
        return "VacationHouse{" +
                "roomsQuantity=" + roomsQuantity +
                ", bedsPerRoom=" + bedsPerRoom +
                "} " + super.toString();
    }

    public void update(VacationHouseDTO vacationHouseDTO) {
        super.update(vacationHouseDTO);
        this.roomsQuantity = vacationHouseDTO.getRoomsQuantity();
        this.bedsPerRoom = vacationHouseDTO.getBedsPerRoom();
    }
}
