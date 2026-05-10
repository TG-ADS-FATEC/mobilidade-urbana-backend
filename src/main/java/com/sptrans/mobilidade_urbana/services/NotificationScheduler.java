package com.sptrans.mobilidade_urbana.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.entities.Notification;
import com.sptrans.mobilidade_urbana.entities.NotificationStatus;
import com.sptrans.mobilidade_urbana.repositories.NotificationRepository;

public class NotificationScheduler {
	
	@Autowired
	private NotificationRepository repository;
	
	@Transactional
	@Scheduled(fixedRate = 60000)
	public void processNotification() {
		
		LocalDateTime now = LocalDateTime.now();
		
		List<Notification> notifications = repository.findPendingDueNotifications(NotificationStatus.PENDING, now);
		
		for(Notification n : notifications) {
			
			send(n);
			
			n.setStatus(NotificationStatus.SENT);
			n.setSentAt(LocalDateTime.now());
		}
		
		repository.saveAll(notifications);
	}
	
	private void send(Notification n) {
		System.out.println("Enviando notificação" + n.getNotificationId());
	}

}
