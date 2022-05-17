package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Review;
import org.jetbrains.annotations.NotNull;

public class ReviewDTO {
    private Long id;
    private Long rentingEntityId;
    private Long rentingOwnerId;
    private Long clientId;
    private Double rating;
    private String comment;

    private ReviewDTO(){}

    public ReviewDTO(@NotNull Review review){
        this.id=review.getId();
        this.rentingEntityId=review.getRentingEntity().getId();
        this.rentingOwnerId=0L;
        this.clientId=review.getClient().getId();
        this.rating=review.getRating();
        this.comment=review.getComment();
    }



    public ReviewDTO(Long id, Long rentingEntityId,Long rentingOwnerId, Long clientId, Double rating, String comment) {
        this.id = id;
        this.rentingEntityId = rentingEntityId;
        this.rentingOwnerId=rentingOwnerId;
        this.clientId = clientId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRentingEntityId() {
        return rentingEntityId;
    }

    public void setRentingEntityId(Long rentingEntityId) {
        this.rentingEntityId = rentingEntityId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public Long getRentingOwnerId() {
        return rentingOwnerId;
    }

    public void setRentingOwnerId(Long rentingOwnerId) {
        this.rentingOwnerId = rentingOwnerId;
    }
}
