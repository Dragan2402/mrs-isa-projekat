package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstSlot", nullable = false)
    private String firstSlot;

    @Column(name = "slotQuantity", nullable = false)
    private Long slotQuantity;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "clientLimit", nullable = false)
    private Integer clientLimit;

    @Column(name = "additionalServices")
    @ElementCollection(targetClass=String.class)
    private Set<String> additionalServices;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renting_entity_id",nullable = false)
    private RentingEntity rentingEntity;

    public Offer() {
    }

    public Offer(String firstSlot, Long slotQuantity, String place, Integer clientLimit, Set<String> additionalServices, Double price) {
        this.firstSlot = firstSlot;
        this.slotQuantity = slotQuantity;
        this.place = place;
        this.clientLimit = clientLimit;
        this.additionalServices = additionalServices;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstSlot() {
        return firstSlot;
    }

    public void setFirstSlot(String firstSlot) {
        this.firstSlot = firstSlot;
    }

    public Long getSlotQuantity() {
        return slotQuantity;
    }

    public void setSlotQuantity(Long slotQuantity) {
        this.slotQuantity = slotQuantity;
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

    public Set<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<String> additionalServices) {
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

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", firstSlot='" + firstSlot + '\'' +
                ", slotQuantity=" + slotQuantity +
                ", place='" + place + '\'' +
                ", clientLimit=" + clientLimit +
                ", additionalServices=" + additionalServices +
                ", price=" + price +
                '}';
    }
}
