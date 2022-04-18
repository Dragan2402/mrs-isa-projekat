package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.FishingClassDTO;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE fishing_class SET deleted = true WHERE id = ?")
public class FishingClass extends RentingEntity{

    @Column(name = "instructor_biography", nullable = false)
    private String instructorBiography;

    @Column(name = "client_limit", nullable = false)
    private Integer clientLimit;

    @Column(name = "fishing_equipment", nullable = false)
    @ElementCollection(targetClass=String.class)
    private Set<String> fishingEquipment;

    @ManyToOne
    @JoinColumn(name = "fishing_instructor_id",nullable = false)
    private FishingInstructor fishingInstructor;

    public FishingClass() {}

    public FishingClass(String name, String address, String promoDescription, List<String> pictures,
                        String behaviourRules, String priceList, String additionalInfo, String cancellationConditions,
                        String instructorBiography, Integer clientLimit, Set<String> fishingEquipment) {
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

    public Set<String> getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(Set<String> fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    public FishingInstructor getFishingInstructor() {
        return fishingInstructor;
    }

    public void setFishingInstructor(FishingInstructor fishingInstructor) {
        this.fishingInstructor = fishingInstructor;
    }

    public void update(FishingClassDTO fishingClassDTO) {
        this.setName(fishingClassDTO.getName());
        this.setAddress(fishingClassDTO.getAddress());
        this.setPromoDescription(fishingClassDTO.getPromoDescription());
        this.setInstructorBiography(fishingClassDTO.getInstructorBiography());
        this.setBehaviourRules(fishingClassDTO.getBehaviourRules());
        this.setPriceList(fishingClassDTO.getPriceList());
        this.setClientLimit(fishingClassDTO.getClientLimit());
        this.setAdditionalInfo(fishingClassDTO.getAdditionalInfo());
        this.setCancellationConditions(fishingClassDTO.getCancellationConditions());
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
