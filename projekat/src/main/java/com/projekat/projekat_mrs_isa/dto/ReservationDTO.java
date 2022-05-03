package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Reservation;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDTO {
    private Long id;
    private String place;
    private Integer clientLimit;
    private List<String> additionalServices;
    private Double price;
    private Long rentingEntityId;
    private Long clientId;
    private LocalDateTime start;
    private Long duration;

    public ReservationDTO() {}

    public ReservationDTO(@NotNull Reservation reservation) {
        this.id = reservation.getId();
        this.place = reservation.getPlace();
        this.clientLimit = reservation.getClientLimit();
        this.additionalServices = reservation.getAdditionalServices();
        this.price = reservation.getPrice();
        this.rentingEntityId = reservation.getRentingEntity().getId();
        this.clientId = reservation.getClient().getId();
        this.start = reservation.getStart();
        this.duration = reservation.getDuration().toMillis();
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
}
