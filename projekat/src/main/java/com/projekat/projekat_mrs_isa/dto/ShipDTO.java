package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Ship;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.List;

public class ShipDTO extends RentingEntityDTO{
    private String type;
    private Integer length;
    private String engineNumber;
    private Integer enginePower;
    private Integer topSpeed;
    private Integer clientLimit;

    public ShipDTO(@NotNull Ship ship) {
        super(ship);
        type = ship.getType();
        length = ship.getLength();
        engineNumber = ship.getEngineNumber();
        enginePower = ship.getEnginePower();
        topSpeed = ship.getTopSpeed();
        clientLimit = ship.getClientLimit();
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

    public Integer getClientLimit() {
        return clientLimit;
    }

    public void setClientLimit(Integer clientLimit) {
        this.clientLimit = clientLimit;
    }
}
