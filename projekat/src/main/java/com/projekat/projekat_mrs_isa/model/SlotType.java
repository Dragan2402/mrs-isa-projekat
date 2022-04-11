package com.projekat.projekat_mrs_isa.model;

public enum SlotType {
    VacationHouse   (24),
    Ship            (1),
    FishingClass    (1);

    private final int hours;

    SlotType(int hours) {
        this.hours = hours;
    }

    private int getHours() {
        return hours;
    }
}
