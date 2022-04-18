package com.projekat.projekat_mrs_isa.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE offer SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "offer" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Slot> slots = new HashSet<Slot>();

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public Offer() {
    }

    public Offer( String place, Integer clientLimit, Set<String> additionalServices, Double price) {
        this.place = place;
        this.clientLimit = clientLimit;
        this.additionalServices = additionalServices;
        this.price = price;
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

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public void addSlot(Slot slot){
        slots.add(slot);
        slot.setOffer(this);
    }

    public void removeSlot(Slot slot){
        slots.remove(slot);
        slot.setOffer(null);
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
                ", deleted=" + deleted +
                '}';
    }
}
