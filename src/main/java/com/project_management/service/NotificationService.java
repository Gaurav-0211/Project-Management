package com.project_management.service;


import com.project_management.model.Response;

import java.time.OffsetDateTime;

public interface NotificationService {

    Response createNotification(Long orgId, Long targetUserId, String title, String body,
                                String priority, OffsetDateTime scheduledAt);

    Response getNotificationById(Long id);

    Response getAllNotifications();

    Response getNotificationsByUser(Long userId);

    Response getPendingNotifications();

    Response updateNotificationStatus(Long id, String newStatus);

    Response deleteNotification(Long id);
}
