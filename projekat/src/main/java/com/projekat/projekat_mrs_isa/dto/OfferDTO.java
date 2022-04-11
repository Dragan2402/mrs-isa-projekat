package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class OfferDTO {
    private Long id;
    private Set<Long> slotIds;
    private String place;
    private Integer clientLimit;
    private Double price;

    public OfferDTO(@NotNull Offer offer) {
        id = offer.getId();

        this.slotIds=new HashSet<Long>();
        for (Slot slot : offer.getSlots()){
            this.slotIds.add(slot.getId());
        }

        place = offer.getPlace();
        clientLimit = offer.getClientLimit();
        price = offer.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Long> getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(Set<Long> slotIds) {
        this.slotIds = slotIds;
    }
}
