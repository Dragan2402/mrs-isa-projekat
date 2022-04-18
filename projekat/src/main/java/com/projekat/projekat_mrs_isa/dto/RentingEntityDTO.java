package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.RentingEntity;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class RentingEntityDTO {
    private Long id;
    private String name;
    private String address;
    private String promoDescription;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
    private String behaviourRules;
    private String priceList;
    private String additionalInfo;
    private String cancellationConditions;

    public RentingEntityDTO() {}

    public RentingEntityDTO(@NotNull RentingEntity rentingEntity) {
        id = rentingEntity.getId();
        name = rentingEntity.getName();
        address = rentingEntity.getAddress();
        promoDescription = rentingEntity.getPromoDescription();
        availableFrom = rentingEntity.getAvailableFrom();
        availableTo = rentingEntity.getAvailableTo();
        behaviourRules = rentingEntity.getBehaviourRules();
        priceList = rentingEntity.getPriceList();
        additionalInfo = rentingEntity.getAdditionalInfo();
        cancellationConditions = rentingEntity.getCancellationConditions();
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
    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }
    public void setAvailableFrom(LocalDateTime availableFrom) {
        this.availableFrom = availableFrom;
    }
    public LocalDateTime getAvailableTo() {
        return availableTo;
    }
    public void setAvailableTo(LocalDateTime availableTo) {
        this.availableTo = availableTo;
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
}
