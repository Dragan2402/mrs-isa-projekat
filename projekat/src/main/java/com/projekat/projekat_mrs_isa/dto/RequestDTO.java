package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Request;
import com.projekat.projekat_mrs_isa.model.RequestType;
import com.projekat.projekat_mrs_isa.model.Review;
import org.jetbrains.annotations.NotNull;

public class RequestDTO {
    private Long id;
    private Long submitterId;
    private String submitterUsername;
    private String text;
    private RequestType type;

    public RequestDTO() {
    }

    public RequestDTO(Long id, Long submitterId, String submitterUsername, String text, RequestType type) {
        this.id = id;
        this.submitterId = submitterId;
        this.submitterUsername = submitterUsername;
        this.text = text;
        this.type = type;
    }

    public RequestDTO(@NotNull Request request){
        this.id=request.getId();
        this.submitterId=request.getSubmitter().getId();
        this.submitterUsername=request.getSubmitter().getUsername();
        this.text=request.getText();
        this.type=request.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Long submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterUsername() {
        return submitterUsername;
    }

    public void setSubmitterUsername(String submitterUsername) {
        this.submitterUsername = submitterUsername;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }
}
