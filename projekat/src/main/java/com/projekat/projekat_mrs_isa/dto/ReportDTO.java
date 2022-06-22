package com.projekat.projekat_mrs_isa.dto;

import com.projekat.projekat_mrs_isa.model.Report;
import com.projekat.projekat_mrs_isa.model.ReportType;
import org.jetbrains.annotations.NotNull;

public class ReportDTO {
    private Long id;
    private String text;
    private ReportType type;
    private Long reservationId;
    private Long clientId;
    private String clientUsername;
    private Long submitterId;
    private String submitterUsername;
    private Boolean reviewed;

    public ReportDTO() {}

    public ReportDTO(String text, ReportType type, Long reservationId, Long clientId, String clientUsername, Long submitterId, String submitterUsername, Boolean reviewed) {
        this.text = text;
        this.type = type;
        this.reservationId = reservationId;
        this.clientId = clientId;
        this.clientUsername = clientUsername;
        this.submitterId = submitterId;
        this.submitterUsername = submitterUsername;
        this.reviewed = reviewed;
    }

    public ReportDTO(@NotNull Report report) {
        this.id = report.getId();
        this.text = report.getText();
        this.type = report.getType();
        this.reservationId = report.getReservation().getId();
        this.clientId = report.getClient().getId();
        this.clientUsername = report.getClient().getUsername();
        this.submitterId = report.getSubmitter().getId();
        this.submitterUsername = report.getSubmitter().getUsername();
        this.reviewed = report.isReviewed();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
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

    public Boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }
}
