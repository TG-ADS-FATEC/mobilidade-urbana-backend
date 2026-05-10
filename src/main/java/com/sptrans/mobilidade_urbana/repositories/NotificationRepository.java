package com.sptrans.mobilidade_urbana.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sptrans.mobilidade_urbana.entities.Notification;
import com.sptrans.mobilidade_urbana.entities.NotificationStatus;
import com.sptrans.mobilidade_urbana.entities.Profile;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByProfile(Profile profile);

	@Query("""
			SELECT n FROM Notification n
			WHERE n.status = :status
			AND n.scheduledTime <= :now
			""")List<Notification> findPendingDueNotifications(@Param("status")NotificationStatus status, @Param("now") LocalDateTime now);

}
