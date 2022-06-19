package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.ShipDTO;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE ship SET deleted = true WHERE id = ?")
public class Ship extends RentingEntity{

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "length", nullable = false)
    private Integer length;

    @Column(name = "engine_number", nullable = false)
    private String engineNumber;

    @Column(name = "engine_power", nullable = false)
    private Integer enginePower;

    @Column(name = "top_speed", nullable = false)
    private Integer topSpeed;

    @Column(name = "navigation_equipment", nullable = false)
    @ElementCollection(targetClass=String.class)
    private Set<String> navigationEquipment;

    @Column(name = "client_limit", nullable = false)
    private Integer clientLimit;

    @Column(name = "fishing_equipment", nullable = false)
    @ElementCollection(targetClass=String.class)
    private Set<String> fishingEquipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_owner_id",nullable = false)
    private ShipOwner shipOwner;

    public Ship() {}

    public Ship(String name, String address, String promoDescription, List<String> pictures, String behaviourRules,
                Double priceList, String additionalInfo, String cancellationConditions, String type, Integer length,
                String engineNumber, Integer enginePower, Integer topSpeed, Set<String> navigationEquipment,
                Integer clientLimit, Set<String> fishingEquipment) {
        super(name, address, promoDescription, pictures, behaviourRules, priceList, additionalInfo, cancellationConditions);
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

    public Set<String> getNavigationEquipment() {
        return navigationEquipment;
    }

    public void setNavigationEquipment(Set<String> navigationEquipment) {
        this.navigationEquipment = navigationEquipment;
    }

    public Integer getClientLimit() {
        return clientLimit;
    }

    public void setClientLimit(Integer clientLimit) {
        this.clientLimit = clientLimit;
    }

    public Set<String> getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(Set<String> fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    public ShipOwner getShipOwner() {
        return shipOwner;
    }

    public void setShipOwner(ShipOwner shipOwner) {
        this.shipOwner = shipOwner;
    }

    @Override
    public String getREType() {
        return "SH";
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

    public void update(ShipDTO shipDTO) {
        super.update(shipDTO);
        this.type = shipDTO.getType();
        this.length = shipDTO.getLength();
        this.engineNumber = shipDTO.getEngineNumber();
        this.enginePower = shipDTO.getEnginePower();
        this.topSpeed = shipDTO.getTopSpeed();
        this.clientLimit = shipDTO.getClientLimit();
    }
}
