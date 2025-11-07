package com.project_management.repository;


import com.project_management.model.Notification;
import com.project_management.model.NotificationDeliveryAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDeliveryAttemptRepository extends JpaRepository<NotificationDeliveryAttempt, Long> {

    List<NotificationDeliveryAttempt> findByNotification(Notification notification);
}
