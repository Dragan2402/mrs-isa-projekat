package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Transaction;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;

    private Long reservationId;

    private String name;

    private Double price;
    private Double fee;

    private LocalDateTime start;

    private Duration duration;

    public TransactionDTO() {}

    public TransactionDTO(@NotNull Transaction transaction) {
        this.id = transaction.getId();
        this.reservationId = transaction.getReservation().getId();
        this.name = transaction.getReservation().getRentingEntity().getName();
        this.price = transaction.getReservation().getPrice();
        this.fee = transaction.getFee();
        this.start = transaction.getReservation().getStart();
        this.duration = transaction.getReservation().getDuration();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
