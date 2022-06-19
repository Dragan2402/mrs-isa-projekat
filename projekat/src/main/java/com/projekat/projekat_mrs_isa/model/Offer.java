package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE offer SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "client_limit", nullable = false)
    private Integer clientLimit;

    @Column(name = "additional_services")
    @ElementCollection(targetClass=String.class,fetch = FetchType.EAGER )
    private List<String> additionalServices;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renting_entity_id",nullable = false)
    private RentingEntity rentingEntity;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "duration", nullable = false)
    private Duration duration;

    @Version
    @Column(name = "version",nullable = false,unique = false)
    private Integer version;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public Offer() {}

    public Offer(OfferDTO offerDTO, RentingEntity rentingEntity) {
        this.place = offerDTO.getPlace();
        this.clientLimit = offerDTO.getClientLimit();
        this.additionalServices = offerDTO.getAdditionalServices();
        this.price = offerDTO.getPrice();
        this.rentingEntity = rentingEntity;
        this.start = offerDTO.getStart();
        this.duration = Duration.ofMillis(offerDTO.getDuration());
        this.deleted = false;
    }

    public Offer( String place, Integer clientLimit, List<String> additionalServices, Double price,
                  RentingEntity rentingEntity, LocalDateTime start, Duration duration) {
        this.place = place;
        this.clientLimit = clientLimit;
        this.additionalServices = additionalServices;
        this.price = price;
        this.rentingEntity = rentingEntity;
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
        return "Offer{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", clientLimit=" + clientLimit +
                ", additionalServices=" + additionalServices +
                ", price=" + price +
                ", rentingEntity=" + rentingEntity.getId() +
                ", start=" + start +
                ", duration=" + duration +
                ", deleted=" + deleted +
                '}';
    }
}
