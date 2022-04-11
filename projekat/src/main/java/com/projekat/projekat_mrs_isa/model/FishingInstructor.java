package com.projekat.projekat_mrs_isa.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FishingInstructor extends  User{

    @OneToMany(mappedBy = "fishingInstructor" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<FishingClass> fishingClasses = new HashSet<FishingClass>();

    public FishingInstructor() {
    }

    public FishingInstructor(String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super(email, password, firstName, lastName, address, city, country, phoneNum);
    }

    public Set<FishingClass> getFishingClasses() {
        return fishingClasses;
    }

    public void setFishingClasses(Set<FishingClass> fishingClasses) {
        this.fishingClasses = fishingClasses;
    }

    public void addFishingClass(FishingClass fishingClass){
        fishingClasses.add(fishingClass);
        fishingClass.setFishingInstructor(this);
    }

    public void removeFishingClass(FishingClass fishingClass){
        fishingClasses.remove(fishingClass);
        fishingClass.setFishingInstructor(null);
    }
}
