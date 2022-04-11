package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class RentingEntity {
    @Id
    @SequenceGenerator(name = "rentingEntitiesSeqGenV1", sequenceName = "rentingEntitiesSeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rentingEntitiesSeqGenV1")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "promoDescription", nullable = false)
    private String promoDescription;

    @Column(name = "pictures", nullable = false)
    @ElementCollection(targetClass=String.class)
    private List<String> pictures;

    @Column(name = "availableReservations", nullable = false)
    @ElementCollection(targetClass=Reservation.class)
    private List<Reservation> availableReservations;

    @Column(name = "behaviourRules", nullable = false)
    private String behaviourRules;

    @Column(name = "priceList", nullable = false)
    private String priceList;

    @Column(name = "additionalInfo", nullable = false)
    private String additionalInfo;

    @Column(name = "cancellationConditions", nullable = false)
    private String cancellationConditions;

    public RentingEntity() {
    }

    public RentingEntity(String name, String address, String promoDescription, List<String> pictures, List<Reservation> availableReservations, String behaviourRules, String priceList, String additionalInfo, String cancellationConditions) {
        this.name = name;
        this.address = address;
        this.promoDescription = promoDescription;
        this.pictures = pictures;
        this.availableReservations = availableReservations;
        this.behaviourRules = behaviourRules;
        this.priceList = priceList;
        this.additionalInfo = additionalInfo;
        this.cancellationConditions = cancellationConditions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<Reservation> getAvailableReservations() {
        return availableReservations;
    }

    public void setAvailableReservations(List<Reservation> availableReservations) {
        this.availableReservations = availableReservations;
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

    @Override
    public String toString() {
        return "RentingEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", promoDescription='" + promoDescription + '\'' +
                ", pictures=" + pictures +
                ", availableReservations=" + availableReservations +
                ", behaviourRules='" + behaviourRules + '\'' +
                ", priceList='" + priceList + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", cancellationConditions='" + cancellationConditions + '\'' +
                '}';
    }
}