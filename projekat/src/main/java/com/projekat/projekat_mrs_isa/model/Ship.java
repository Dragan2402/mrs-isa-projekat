package com.projekat.projekat_mrs_isa.model;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Ship extends RentingEntity{

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "length", nullable = false)
    private Integer length;

    @Column(name = "engineNumber", nullable = false)
    private String engineNumber;

    @Column(name = "enginePower", nullable = false)
    private Integer enginePower;

    @Column(name = "topSpeed", nullable = false)
    private Integer topSpeed;

    @Column(name = "navigationEquipment", nullable = false)
    @ElementCollection(targetClass=String.class)
    private List<String> navigationEquipment;

    @Column(name = "clientLimit", nullable = false)
    private Integer clientLimit;

    @Column(name = "fishingEquipment", nullable = false)
    @ElementCollection(targetClass=String.class)
    private List<String> fishingEquipment;

    public Ship() {
    }

    public Ship(String name, String address, String promoDescription, List<String> pictures, List<Offer> availableOffers, String behaviourRules, String priceList, String additionalInfo, String cancellationConditions, String type, Integer length, String engineNumber, Integer enginePower, Integer topSpeed, List<String> navigationEquipment, Integer clientLimit, List<String> fishingEquipment) {
        super(name, address, promoDescription, pictures, availableOffers, behaviourRules, priceList, additionalInfo, cancellationConditions);
        this.type = type;
        this.length = length;
        this.engineNumber = engineNumber;
        this.enginePower = enginePower;
        this.topSpeed = topSpeed;
        this.navigationEquipment = navigationEquipment;
        this.clientLimit = clientLimit;
        this.fishingEquipment = fishingEquipment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Integer enginePower) {
        this.enginePower = enginePower;
    }

    public Integer getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(Integer topSpeed) {
        this.topSpeed = topSpeed;
    }

    public List<String> getNavigationEquipment() {
        return navigationEquipment;
    }

    public void setNavigationEquipment(List<String> navigationEquipment) {
        this.navigationEquipment = navigationEquipment;
    }

    public Integer getClientLimit() {
        return clientLimit;
    }

    public void setClientLimit(Integer clientLimit) {
        this.clientLimit = clientLimit;
    }

    public List<String> getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(List<String> fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "type='" + type + '\'' +
                ", length=" + length +
                ", engineNumber='" + engineNumber + '\'' +
                ", enginePower=" + enginePower +
                ", topSpeed=" + topSpeed +
                ", navigationEquipment=" + navigationEquipment +
                ", clientLimit=" + clientLimit +
                ", fishingEquipment=" + fishingEquipment +
                "} " + super.toString();
    }
}
