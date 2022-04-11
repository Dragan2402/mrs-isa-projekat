package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;
import java.time.LocalDateTime;




@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startingTime;
    private int durationHours;
    private boolean free;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renting_entitiy_id",nullable = false)
    private RentingEntity rentingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;


    public Slot() {
    }

    public Slot(LocalDateTime startingTime, int durationHours, boolean free) {
        this.startingTime = startingTime;
        this.durationHours = durationHours;
        this.free = free;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public RentingEntity getRentingEntity() {
        return rentingEntity;
    }

    public void setRentingEntity(RentingEntity rentingEntity) {
        this.rentingEntity = rentingEntity;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
