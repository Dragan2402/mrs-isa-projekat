package com.projekat.projekat_mrs_isa.dto;


import com.projekat.projekat_mrs_isa.model.Complaint;
import org.jetbrains.annotations.NotNull;

public class ComplaintDTO {
    private Long id;
    private Long complainantId;
    private Long rentingEntityId;
    private Long respodentId;
    private String text;
    private Boolean approved;

    private String entityName;

    private String ownerName;

    private String clientName;

    public ComplaintDTO() {
    }

    public ComplaintDTO(Long id, Long complainantId, Long rentingEntityId, Long respodentId, String text, Boolean approved) {
        this.id = id;
        this.complainantId = complainantId;
        this.rentingEntityId = rentingEntityId;
        this.respodentId = respodentId;
        this.text = text;
        this.approved = approved;
    }

    public ComplaintDTO(@NotNull Complaint complaint) {
        id = complaint.getId();
        complainantId = complaint.getComplainant().getId();
        if (complaint.getRentingEntity() != null) {
            rentingEntityId = complaint.getRentingEntity().getId();
            entityName = complaint.getRentingEntity().getName();
        } else {
            rentingEntityId = 0L;
            entityName = null;
        }
        if (complaint.getRespondent() != null){
            respodentId = complaint.getRespondent().getId();
            ownerName = complaint.getRespondent().getUsername();
        }
        else{
            respodentId = 0L;
            ownerName = null;
        }
        clientName = complaint.getComplainant().getUsername();
        text = complaint.getText();
        approved = complaint.isApproved();
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
