package com.projekat.projekat_mrs_isa.dto;



public class ComplaintDTO {
    private Long id;
    private Long complainantId;
    private Long rentingEntityId;
    private Long respodentId;
    private String text;

    public ComplaintDTO(){}

    public ComplaintDTO(Long id,Long complainantId,Long rentingEntityId,Long respodentId,String text){
        this.id=id;
        this.complainantId=complainantId;
        this.rentingEntityId=rentingEntityId;
        this.respodentId=respodentId;
        this.text=text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComplainantId() {
        return complainantId;
    }

    public void setComplainantId(Long complainantId) {
        this.complainantId = complainantId;
    }

    public Long getRentingEntityId() {
        return rentingEntityId;
    }

    public void setRentingEntityId(Long rentingEntityId) {
        this.rentingEntityId = rentingEntityId;
    }

    public Long getRespodentId() {
        return respodentId;
    }

    public void setRespodentId(Long respodentId) {
        this.respodentId = respodentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
