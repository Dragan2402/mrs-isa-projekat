package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.RentingEntity;
import org.jetbrains.annotations.NotNull;

public class SubscriptionDTO {
    private Long id;
    private String name;
    private String img;

    public SubscriptionDTO(){}

    public SubscriptionDTO(@NotNull RentingEntity rentingEntity){
        id=rentingEntity.getId();
        name=rentingEntity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
