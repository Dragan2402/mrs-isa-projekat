package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Fee;
import org.jetbrains.annotations.NotNull;

public class FeeDTO {

    private Long id;

    private Double value;

    public FeeDTO() {}

    public FeeDTO(@NotNull Fee fee){
        this.id = fee.getId();
        this.value = fee.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
