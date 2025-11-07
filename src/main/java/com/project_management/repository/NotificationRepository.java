package com.project_management.repository;


import com.project_management.model.Notification;
import com.project_management.model.NotificationPriority;
import com.project_management.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.status = :status AND n.priority = :priority ORDER BY n.createdAt ASC")
    List<Notification> findByStatusAndPriority(NotificationStatus status, NotificationPriority priority);

    @Query("SELECT n FROM Notification n WHERE n.status = 'PENDING' AND n.scheduledAt <= :now ORDER BY n.scheduledAt ASC")
    List<Notification> findScheduledDueNotifications(OffsetDateTime now);

    @Query("SELECT n FROM Notification n WHERE n.status = 'FAILED' AND size(n.deliveryAttempts) < :maxAttempts")
    List<Notification> findRetryableFailedNotifications(int maxAttempts);
}
