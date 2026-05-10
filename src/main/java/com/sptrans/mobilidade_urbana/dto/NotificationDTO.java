package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;

import com.sptrans.mobilidade_urbana.entities.Notification;
import com.sptrans.mobilidade_urbana.entities.NotificationStatus;

public class NotificationDTO {
	
	private Long notificationId;
	private LocalDateTime scheduledTime;
	private LocalDateTime sentAt;
	private NotificationStatus status;
	private LocalDateTime createdAt;
	
	public NotificationDTO() {}

	public NotificationDTO(Long notificationId, LocalDateTime scheduledTime, LocalDateTime sentAt,
			NotificationStatus status, LocalDateTime createdAt) {
		super();
		this.notificationId = notificationId;
		this.scheduledTime = scheduledTime;
		this.sentAt = sentAt;
		this.status = status;
		this.createdAt = createdAt;
	}
	
	public NotificationDTO(Notification entity) {
		notificationId = entity.getNotificationId();
		scheduledTime = entity.getScheduledTime();
		sentAt = entity.getSentAt();
		status = entity.getStatus();
		createdAt = entity.getCreatedAt();
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public NotificationStatus getStatus() {
		return status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

}
