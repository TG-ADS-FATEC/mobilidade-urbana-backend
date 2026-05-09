package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;

import com.sptrans.mobilidade_urbana.entities.Alert;

public class AlertDTO {
	private Long alertId;
	private Integer minutesBefore;
	private Boolean active;
	private LocalDateTime createdAt;
	
	public AlertDTO() {}

	public AlertDTO(Long alertId, Integer minutesBefore, Boolean active, LocalDateTime createdAt) {
		super();
		this.alertId = alertId;
		this.minutesBefore = minutesBefore;
		this.active = active;
		this.createdAt = createdAt;
	}
	
	public AlertDTO(Alert entity) {
		alertId = entity.getAlertId();
		minutesBefore = entity.getMinutesBefore();
		active = entity.getActive();
		createdAt = entity.getCreatedAt();
	}

	public Long getAlertId() {
		return alertId;
	}

	public Integer getMinutesBefore() {
		return minutesBefore;
	}

	public Boolean getActive() {
		return active;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	

}
