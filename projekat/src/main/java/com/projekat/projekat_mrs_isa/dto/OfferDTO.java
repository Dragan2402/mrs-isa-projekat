package com.projekat.projekat_mrs_isa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projekat.projekat_mrs_isa.model.Offer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OfferDTO {
    private Long id;
    private String place;
    private Integer clientLimit;
    private List<String> additionalServices;
    private Double price;
    private Long rentingEntityId;
    @JsonFormat(pattern = ("dd.MM.yyyy HH:mm"))
    private LocalDateTime start;
    private Long duration;

    public OfferDTO() {}

    public OfferDTO(@NotNull Offer offer) {
        this.id = offer.getId();
        this.place = offer.getPlace();
        this.clientLimit = offer.getClientLimit();
        this.additionalServices = offer.getAdditionalServices();
        this.price = offer.getPrice();
        this.rentingEntityId = offer.getRentingEntity().getId();
        this.start = offer.getStart();
        this.duration = offer.getDuration().toMillis();
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
    public List<String> getAdditionalServices() {
        return additionalServices;
    }
    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
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
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
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
