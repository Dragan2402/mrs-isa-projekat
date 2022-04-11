package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Slot;
import com.projekat.projekat_mrs_isa.model.SlotType;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class SlotDTO {
    private Long id;
    private LocalDateTime startingTime;
    private SlotType type;
    private boolean free;

    public SlotDTO(@NotNull Slot slot) {
        this.id= slot.getId();
        this.startingTime = slot.getStartingTime();
        this.type=slot.getType();
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

    public SlotType getType() {
        return type;
    }

    public void setType(SlotType type) {
        this.type = type;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
