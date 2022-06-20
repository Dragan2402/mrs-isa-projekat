package com.projekat.projekat_mrs_isa.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted=false")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renting_entity_id")
    private RentingEntity rentingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renting_owner_id")
    private User rentingOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "client_id",nullable = false)
    private Client client;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    public Review(){}

    public Review(RentingEntity rentingEntity,Client client,Double rating){
        this.rentingEntity=rentingEntity;
        this.rentingOwner=null;
        this.client=client;
        this.rating=rating;
        this.comment="";
        this.deleted=false;
        this.approved=false;
    }

    public Review(RentingEntity rentingEntity,Client client,Double rating,String comment){
        this.rentingEntity=rentingEntity;
        this.rentingOwner=null;
        this.client=client;
        this.rating=rating;
        this.comment=comment;
        this.deleted=false;
        this.approved=false;
    }

    public Review(User rentingEntityOwner,Client client,Double rating,String comment){
        this.rentingOwner=rentingEntityOwner;
        this.rentingEntity=null;
        this.client=client;
        this.rating=rating;
        this.comment=comment;
        this.deleted=false;
        this.approved=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RentingEntity getRentingEntity() {
        return rentingEntity;
    }

    public void setRentingEntity(RentingEntity rentingEntity) {
        this.rentingEntity = rentingEntity;
    }

    public User getRentingOwner() {
        return rentingOwner;
    }

    public void setRentingOwner(User rentingOwner) {
        this.rentingOwner = rentingOwner;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rentingEntity=" + rentingEntity +
                ", client=" + client +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
