package com.projekat.projekat_mrs_isa.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE fishing_instructor SET deleted = true WHERE id = ?")
public class FishingInstructor extends  User{

    @OneToMany(mappedBy = "fishingInstructor" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<FishingClass> fishingClasses = new HashSet<>();

    @Column(name = "additional_services")
    @ElementCollection(targetClass=String.class,fetch = FetchType.LAZY)
    private List<String> additionalServices = new ArrayList<>();

    public FishingInstructor() {}

    public FishingInstructor(String email,String username, String password,String picture, String firstName, String lastName, String address,
                             String city, String country, String phoneNum,List<String> additionalServices) {
        super(email,username, password, picture,firstName, lastName, address, city, country, phoneNum);
        super.setType(AccountType.INSTRUCTOR);
        this.additionalServices=additionalServices;
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

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void addAditionalService(String addService){
        this.additionalServices.add(addService);
    }
}
