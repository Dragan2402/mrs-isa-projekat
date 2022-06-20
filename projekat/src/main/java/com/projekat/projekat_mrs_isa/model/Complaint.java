package com.projekat.projekat_mrs_isa.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted=false")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "complainant_id",nullable = false)
    private User complainant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renting_entity_id")
    private RentingEntity rentingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respondent_id")
    private User respondent;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    public Complaint(){}

    public Complaint(User complainant,RentingEntity rentingEntity,String text){
        this.complainant=complainant;
        this.rentingEntity=rentingEntity;
        this.respondent=null;
        this.text=text;
        this.deleted=false;
        this.approved=false;
    }

    public Complaint(User complainant,User respondent,String text){
        this.complainant=complainant;
        this.rentingEntity=null;
        this.respondent=respondent;
        this.text=text;
        this.deleted=false;
        this.approved=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getComplainant() {
        return complainant;
    }

    public void setComplainant(User complainant) {
        this.complainant = complainant;
    }

    public RentingEntity getRentingEntity() {
        return rentingEntity;
    }

    public void setRentingEntity(RentingEntity rentingEntity) {
        this.rentingEntity = rentingEntity;
    }

    public User getRespondent() {
        return respondent;
    }

    public void setRespondent(User respondent) {
        this.respondent = respondent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return "Complaint{" +
                "id=" + id +
                ", complainant=" + complainant +
                ", rentingEntity=" + rentingEntity +
                ", respondent=" + respondent +
                ", text='" + text + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
