package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.VacationHouse;
import org.jetbrains.annotations.NotNull;

public class VacationHouseDTO extends RentingEntityDTO {
    private Integer roomsQuantity;
    private Integer bedsPerRoom;

    public VacationHouseDTO(@NotNull VacationHouse vacationHouse) {
        super(vacationHouse);
        roomsQuantity = vacationHouse.getRoomsQuantity();
        bedsPerRoom = vacationHouse.getBedsPerRoom();
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
}
