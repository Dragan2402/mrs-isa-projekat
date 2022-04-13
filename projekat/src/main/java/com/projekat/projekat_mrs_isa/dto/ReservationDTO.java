package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.Reservation;
import com.projekat.projekat_mrs_isa.model.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ReservationDTO {
    private Long id;
    private String place;
    private Set<Long> slotIds;
    private Integer clientNum;
    private Double price;

    public ReservationDTO() {

    }

    public ReservationDTO(@NotNull Reservation reservation) {
        id = reservation.getId();
        this.slotIds=new HashSet<Long>();
        for (Slot slot : reservation.getSlots()){
            this.slotIds.add(slot.getId());
        }
        place = reservation.getPlace();
        clientNum = reservation.getClientNum();
        price = reservation.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(Set<Long> slotIds) {
        this.slotIds = slotIds;
    }

    public Integer getClientNum() {
        return clientNum;
    }

    public void setClientNum(Integer clientNum) {
        this.clientNum = clientNum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
