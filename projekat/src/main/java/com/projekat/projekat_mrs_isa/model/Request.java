package com.projekat.projekat_mrs_isa.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted=false")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "submitter_id",nullable = false)
    private User submitter;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "type", nullable = false)
    private RequestType type;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    public Request(){}

    public Request(User submitter,String text,RequestType requestType){
        this.submitter=submitter;
        this.text=text;
        this.type=requestType;
        this.deleted=false;
        this.approved=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
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

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", submitter=" + submitter +
                ", text='" + text + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
