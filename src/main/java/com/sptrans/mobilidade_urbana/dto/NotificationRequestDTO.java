package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;

public class NotificationRequestDTO {
	
	private Long alertId;
	private LocalDateTime scheduledTime;
	
	public NotificationRequestDTO() {}

	public NotificationRequestDTO(Long alertId, LocalDateTime scheduledTime) {
		super();
		this.alertId = alertId;
		this.scheduledTime = scheduledTime;
	}

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	
	

}
