package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Offer;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class OfferDTO {
    private Long id;
    private String place;
    private Integer clientLimit;
    private Double price;
    private Long rentingEntityId;
    private LocalDateTime start;
    private Duration duration;

    public OfferDTO() {}

    public OfferDTO(@NotNull Offer offer) {
        this.id = offer.getId();
        this.place = offer.getPlace();
        this.clientLimit = offer.getClientLimit();
        this.price = offer.getPrice();
        this.rentingEntityId = offer.getRentingEntity().getId();
        this.start = offer.getStart();
        this.duration = offer.getDuration();
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
    public Long getRentingEntityId() {
        return rentingEntityId;
    }
    public void setRentingEntityId(Long rentingEntityId) {
        this.rentingEntityId = rentingEntityId;
    }
    public LocalDateTime getStart() {
        return start;
    }
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    public Duration getDuration() {
        return duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", clientLimit=" + clientLimit +
                ", price=" + price +
                ", rentingEntityId=" + rentingEntityId +
                ", start=" + start +
                ", duration=" + duration +
                '}';
    }
}
