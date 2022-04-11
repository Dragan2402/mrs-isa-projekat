package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Offer;
import org.jetbrains.annotations.NotNull;

public class OfferDTO {
    private Integer id;
    private String firstSlot;
    private Long slotQuantity;
    private String place;
    private Integer clientLimit;
    private Double price;

    public OfferDTO(@NotNull Offer offer) {
        id = offer.getId();
        firstSlot = offer.getFirstSlot();
        slotQuantity = offer.getSlotQuantity();
        place = offer.getPlace();
        clientLimit = offer.getClientLimit();
        price = offer.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return clientLimit;
    }

    public void setClientLimit(Integer clientLimit) {
        this.clientLimit = clientLimit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
