package com.projekat.projekat_mrs_isa.dto;

public class ReviewDisplayDTO {
    private Double rating;
    private String commenter;
    private String commenterImage;
    private String entityName;
    private String ownerName;
    private String text;
    private boolean forOwner;

    public ReviewDisplayDTO() {
    }

    public ReviewDisplayDTO(Double rating, String commenter,String commenterImage, String entityName,String ownerName,String text, boolean forOwner) {
        this.rating = rating;
        this.commenter = commenter;
        this.commenterImage=commenterImage;
        this.entityName=entityName;
        this.ownerName=ownerName;
        this.text = text;
        this.forOwner = forOwner;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isForOwner() {
        return forOwner;
    }

    public void setForOwner(boolean forOwner) {
        this.forOwner = forOwner;
    }

    public String getCommenterImage() {
        return commenterImage;
    }

    public void setCommenterImage(String commenterImage) {
        this.commenterImage = commenterImage;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
