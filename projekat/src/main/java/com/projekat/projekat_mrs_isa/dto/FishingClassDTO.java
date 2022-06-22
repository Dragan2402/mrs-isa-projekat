package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.FishingClass;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FishingClassDTO extends RentingEntityDTO {
    private String instructorBiography;
    private Integer clientLimit;

    private Set<String> fishingEquipment;

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

    public Set<String> getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(Set<String> fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }
}
