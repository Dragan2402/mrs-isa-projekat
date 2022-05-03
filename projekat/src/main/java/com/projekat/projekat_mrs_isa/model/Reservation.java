package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE reservation SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "client_limit", nullable = false)
    private Integer clientLimit;

    @Column(name = "additional_services")
    @ElementCollection(targetClass=String.class)
    private List<String> additionalServices = new ArrayList<>();

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renting_entity_id",nullable = false)
    private RentingEntity rentingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id",nullable = false)
    private Client client;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "duration", nullable = false)
    private Duration duration;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public Reservation() {}

    public Reservation(Offer offer, Client client) {
        this.place = offer.getPlace();
        this.clientLimit = offer.getClientLimit();
        this.additionalServices = offer.getAdditionalServices();
        this.price = offer.getPrice();
        this.rentingEntity = offer.getRentingEntity();
        this.client = client;
        this.start = offer.getStart();
        this.duration = offer.getDuration();
        this.deleted = false;
    }

    public Reservation(ReservationDTO reservationDTO, RentingEntity rentingEntity, Client client) {
        this.place = reservationDTO.getPlace();
        this.clientLimit = reservationDTO.getClientLimit();
        this.additionalServices = reservationDTO.getAdditionalServices();
        this.price = reservationDTO.getPrice();
        this.rentingEntity = rentingEntity;
        this.client = client;
        this.start = reservationDTO.getStart();
        this.duration = Duration.ofMillis(reservationDTO.getDuration());
        this.deleted = false;
    }

    public Reservation( String place, Integer clientLimit, List<String> additionalServices, Double price,
                        RentingEntity rentingEntity, Client client, LocalDateTime start, Duration duration) {

        this.place = place;
        this.clientLimit = clientLimit;
        this.additionalServices = additionalServices;
        this.price = price;
        this.rentingEntity = rentingEntity;
        this.client = client;
        this.start = start;
        this.duration = duration;
        this.deleted = false;
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

    public RentingEntity getRentingEntity() {
        return rentingEntity;
    }

    public void setRentingEntity(RentingEntity rentingEntity) {
        this.rentingEntity = rentingEntity;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", clientLimit=" + clientLimit +
                ", additionalServices=" + additionalServices +
                ", price=" + price +
                ", rentingEntity=" + rentingEntity.getId() +
                ", client=" + client.getId() +
                ", start=" + start +
                ", duration=" + duration +
                ", deleted=" + deleted +
                '}';
    }
}
