package com.projekat.projekat_mrs_isa.model;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Reservation {
    @Id
    @SequenceGenerator(name = "reservationsSeqGenV1", sequenceName = "reservationsSeqV1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservationsSeqGenV1")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "starting_date_time", nullable = false)
    private LocalDateTime startingDateTime;

    @Column(name = "duration", nullable = false)
    private Duration duration;

    @Column(name = "client_limit", nullable = false)
    private Integer clientLimit;

    @Column(name = "additional_services")
    private Set<String> additionalServices;

    @Column(name = "price", nullable = false)
    private Double price;

    public Reservation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartingDateTime() {
        return startingDateTime;
    }

    public void setStartingDateTime(LocalDateTime startingDateTime) {
        this.startingDateTime = startingDateTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getClientLimit() {
        return clientLimit;
    }

    public void setClientLimit(Integer clientLimit) {
        this.clientLimit = clientLimit;
    }

    public Set<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
