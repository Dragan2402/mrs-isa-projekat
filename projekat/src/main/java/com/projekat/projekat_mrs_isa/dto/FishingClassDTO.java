package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.FishingClass;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import org.jetbrains.annotations.NotNull;

public class FishingClassDTO extends RentingEntityDTO {
    private String instructorBiography;
    private Integer clientLimit;

    public FishingClassDTO() {
    }

    public FishingClassDTO(@NotNull FishingClass fishingClass) {
        super(fishingClass);
        instructorBiography = fishingClass.getInstructorBiography();
        clientLimit = fishingClass.getClientLimit();
    }

    public String getInstructorBiography() {
        return instructorBiography;
    }

    public void setInstructorBiography(String instructorBiography) {
        this.instructorBiography = instructorBiography;
    }

    public Integer getClientLimit() {
        return clientLimit;
    }

    public void setClientLimit(Integer clientLimit) {
        this.clientLimit = clientLimit;
    }
}
