package com.projekat.projekat_mrs_isa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class RentingEntityDTO {
    private Long id;
    private String name;
    private String img;
    private String address;
    private Long ownerId;
    private String promoDescription;
    @JsonFormat(pattern = ("dd.MM.yyyy HH:mm"))
    private LocalDateTime availableFrom;
    @JsonFormat(pattern = ("dd.MM.yyyy HH:mm"))
    private LocalDateTime availableTo;
    private String behaviourRules;
    private Double priceList;
    private Double rating;
    private Integer reviewsNumber;
    private String additionalInfo;
    private String cancellationConditions;
    private Boolean isSubscribed;

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
        rating=rentingEntity.getRating();
        reviewsNumber=rentingEntity.getReviewsNumber();
        ownerId=0L;
        isSubscribed=false;
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
    public Double getPriceList() {
        return priceList;
    }
    public void setPriceList(Double priceList) {
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    public Integer getReviewsNumber() {
        return reviewsNumber;
    }
    public void setReviewsNumber(Integer reviewsNumber) {
        this.reviewsNumber = reviewsNumber;
    }
}
