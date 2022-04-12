package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class RentingEntity {
    @Id
    @SequenceGenerator(name = "rentingEntitiesSeqGenV1", sequenceName = "rentingEntitiesSeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rentingEntitiesSeqGenV1")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "promoDescription", nullable = false)
    private String promoDescription;

    @Column(name = "pictures", nullable = false)
    @ElementCollection(targetClass=String.class)
    private Set<String> pictures;

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Slot> slots = new HashSet<Slot>();

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Offer> offers = new HashSet<Offer>();

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @Column(name = "behaviourRules", nullable = false)
    private String behaviourRules;

    @Column(name = "priceList", nullable = false)
    private String priceList;

    @Column(name = "additionalInfo", nullable = false)
    private String additionalInfo;

    @Column(name = "cancellationConditions", nullable = false)
    private String cancellationConditions;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public RentingEntity() {
    }

    public RentingEntity(String name, String address, String promoDescription, Set<String> pictures, String behaviourRules, String priceList, String additionalInfo, String cancellationConditions) {
        this.name = name;
        this.address = address;
        this.promoDescription = promoDescription;
        this.pictures = pictures;
        this.behaviourRules = behaviourRules;
        this.priceList = priceList;
        this.additionalInfo = additionalInfo;
        this.cancellationConditions = cancellationConditions;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public Set<String> getPictures() {
        return pictures;
    }

    public void setPictures(Set<String> pictures) {
        this.pictures = pictures;
    }

    public String getBehaviourRules() {
        return behaviourRules;
    }

    public void setBehaviourRules(String behaviourRules) {
        this.behaviourRules = behaviourRules;
    }

    public String getPriceList() {
        return priceList;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCancellationConditions() {
        return cancellationConditions;
    }

    public void setCancellationConditions(String cancellationConditions) {
        this.cancellationConditions = cancellationConditions;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public void addOffer(Offer offer){
        offers.add(offer);
        offer.setRentingEntity(this);
    }

    public void removeOffer(Offer offer){
        offers.remove(offer);
        offer.setRentingEntity(null);
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
        reservation.setRentingEntity(this);
    }

    public void removeReservation(Reservation reservation){
        reservations.remove(reservation);
        reservation.setRentingEntity(null);
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public void addSlot(Slot slot){
        slots.add(slot);
        slot.setRentingEntity(this);
    }

    public void removeSlot(Slot slot){
        slots.remove(slot);
        slot.setRentingEntity(null);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "RentingEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", promoDescription='" + promoDescription + '\'' +
                ", pictures=" + pictures +
                ", behaviourRules='" + behaviourRules + '\'' +
                ", priceList='" + priceList + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", cancellationConditions='" + cancellationConditions + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}