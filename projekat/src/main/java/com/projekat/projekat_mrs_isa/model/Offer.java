package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    public Offer() {
    }

    public Offer(Integer id, String firstSlot, Long slotQuantity, String place, Integer clientLimit, Set<String> additionalServices, Double price) {
        this.id = id;
        this.firstSlot = firstSlot;
        this.slotQuantity = slotQuantity;
        this.place = place;
        this.clientLimit = clientLimit;
        this.additionalServices = additionalServices;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
