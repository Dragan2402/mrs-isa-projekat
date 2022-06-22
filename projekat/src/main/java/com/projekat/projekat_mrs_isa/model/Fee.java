package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;

@Entity
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", nullable = false)
    private Double value;

    public Fee() {}

    public Fee(Double value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "Fee{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
