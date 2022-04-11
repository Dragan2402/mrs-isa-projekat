package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class SlotDTO {
    private Long id;
    private LocalDateTime startingTime;
    private int durationHours;
    private boolean free;

    public SlotDTO(@NotNull Slot slot) {
        this.id= slot.getId();
        this.startingTime = slot.getStartingTime();
        this.durationHours=slot.getDurationHours();
        this.free=slot.isFree();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
