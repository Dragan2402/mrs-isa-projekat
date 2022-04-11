package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.Reservation;
import org.jetbrains.annotations.NotNull;

public class ReservationDTO {
    private Long id;
    private String firstSlot;
    private Long slotQuantity;
    private String place;
    private Integer clientNum;
    private Double price;

    public ReservationDTO(@NotNull Reservation reservation) {
        id = reservation.getId();
        firstSlot = reservation.getFirstSlot();
        slotQuantity = reservation.getSlotQuantity();
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

    public String getFirstSlot() {
        return firstSlot;
    }

    public void setFirstSlot(String firstSlot) {
        this.firstSlot = firstSlot;
    }

    public Long getSlotQuantity() {
        return slotQuantity;
    }

    public void setSlotQuantity(Long slotQuantity) {
        this.slotQuantity = slotQuantity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getClientLimit() {
        return clientNum;
    }

    public void setClientLimit(Integer clientLimit) {
        this.clientNum = clientLimit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
