package com.projekat.projekat_mrs_isa.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationRequestDTO {
    private String place;
    private Integer clientLimit;
    private List<String> additionalServices;
    private Double price;
    private Long rentingEntityId;
    private LocalDateTime start;
    private LocalDateTime end;

    public ReservationRequestDTO() {
    }

    public ReservationRequestDTO(String place, Integer clientLimit, List<String> additionalServices, Double price, Long rentingEntityId, LocalDateTime start, LocalDateTime end) {
        this.place = place;
        this.clientLimit = clientLimit;
        this.additionalServices = additionalServices;
        this.price = price;
        this.rentingEntityId = rentingEntityId;
        this.start = start;
        this.end = end;
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

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ReservationRequestDTO{" +
                "place='" + place + '\'' +
                ", clientLimit=" + clientLimit +
                ", additionalServices=" + additionalServices +
                ", price=" + price +
                ", rentingEntityId=" + rentingEntityId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
