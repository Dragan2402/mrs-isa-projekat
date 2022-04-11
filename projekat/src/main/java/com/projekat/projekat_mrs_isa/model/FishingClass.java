package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class FishingClass extends RentingEntity{

    @Column(name = "instructorBiography", nullable = false)
    private String instructorBiography;

    @Column(name = "clientLimit", nullable = false)
    private Integer clientLimit;

    @Column(name = "fishingEquipment", nullable = false)
    @ElementCollection(targetClass=String.class)
    private List<String> fishingEquipment;


    @ManyToOne
    @JoinColumn(name = "fishing_instructor_id",nullable = false)
    private FishingInstructor fishingInstructor;


    public FishingClass() {
    }

    public FishingClass(String name, String address, String promoDescription, List<String> pictures, String behaviourRules, String priceList, String additionalInfo, String cancellationConditions, String instructorBiography, Integer clientLimit, List<String> fishingEquipment) {
        super(name, address, promoDescription, pictures, behaviourRules, priceList, additionalInfo, cancellationConditions);
        this.instructorBiography = instructorBiography;
        this.clientLimit = clientLimit;
        this.fishingEquipment = fishingEquipment;
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

    public List<String> getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(List<String> fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    public FishingInstructor getFishingInstructor() {
        return fishingInstructor;
    }

    public void setFishingInstructor(FishingInstructor fishingInstructor) {
        this.fishingInstructor = fishingInstructor;
    }



    @Override
    public String toString() {
        return "FishingClass{" +
                "instructorBiography='" + instructorBiography + '\'' +
                ", clientLimit=" + clientLimit +
                ", fishingEquipment='" + fishingEquipment + '\'' +
                "} " + super.toString();
    }
}
