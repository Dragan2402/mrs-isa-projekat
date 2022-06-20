package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation", nullable = false)
    private Reservation reservation;

    @Column(name = "fee", nullable = false)
    private Double fee;

    public Transaction() {
    }

    public Transaction(Reservation reservation, Double fee) {
        this.reservation = reservation;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", reservation=" + reservation +
                ", fee=" + fee +
                '}';
    }
}
