package com.projekat.projekat_mrs_isa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class TakenPeriodDTO {

    private String startsAt;
    private String endsAt;

    @JsonFormat(pattern = ("dd.MM.yyyy HH:mm"))
    private LocalDateTime takenFrom;
    @JsonFormat(pattern = ("dd.MM.yyyy HH:mm"))
    private LocalDateTime takenTo;

    private String type;

    public TakenPeriodDTO(){}

    public TakenPeriodDTO(LocalDateTime takenFromP, LocalDateTime takenToP,String typeP){
        takenFrom=takenFromP;
        takenTo = takenToP;
        type=typeP;
        startsAt=type+" starts at: "+takenFrom.getHour()+":"+takenFrom.getMinute();
        endsAt=type+" ends at: "+takenTo.getHour()+":"+takenTo.getMinute();
    }


    public LocalDateTime getTakenFrom() {
        return takenFrom;
    }

    public void setTakenFrom(LocalDateTime takenFrom) {
        this.takenFrom = takenFrom;
    }

    public LocalDateTime getTakenTo() {
        return takenTo;
    }

    public void setTakenTo(LocalDateTime takenTo) {
        this.takenTo = takenTo;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
