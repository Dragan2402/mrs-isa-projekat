package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

public class RentingEntityDTO {
    private Long id;
    private String name;
    private String address;
    private String promoDescription;
    private String behaviourRules;
    private String priceList;
    private String additionalInfo;
    private String cancellationConditions;

    public RentingEntityDTO() {
    }

    public RentingEntityDTO(@NotNull RentingEntity rentingEntity) {
        id = rentingEntity.getId();
        name = rentingEntity.getName();
        address = rentingEntity.getAddress();
        promoDescription = rentingEntity.getPromoDescription();
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
