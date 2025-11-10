package com.project_management.controller;

import com.project_management.model.Response;
import com.project_management.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Create a new notification
    @PostMapping
    public ResponseEntity<Response> createNotification(
            @RequestParam Long orgId,
            @RequestParam Long targetUserId,
            @RequestParam String title,
            @RequestParam(required = false) String body,
            @RequestParam(defaultValue = "NORMAL") String priority,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime scheduledAt) {

        log.info("Creating new notification | orgId: {}, targetUserId: {}, title: '{}', priority: {}, scheduledAt: {}",
                orgId, targetUserId, title, priority, scheduledAt);

        Response res = notificationService.createNotification(orgId, targetUserId, title, body, priority, scheduledAt);

        log.info("Notification creation result | Status: {}, Message: {}", res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get all notifications
    @GetMapping
    public ResponseEntity<Response> getAllNotifications() {
        log.info("Fetching all notifications");

        Response res = notificationService.getAllNotifications();

        log.info("Retrieved {} notifications",
                (res.getData() instanceof java.util.Collection) ? ((java.util.Collection<?>) res.getData()).size() : 0);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getNotificationById(@PathVariable Long id) {
        log.info("Fetching notification by ID: {}", id);

        Response res = notificationService.getNotificationById(id);

        log.info("Fetched notification | Status: {}, Message: {}", res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get notifications by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response> getNotificationsByUser(@PathVariable Long userId) {
        log.info("👤 Fetching notifications for user ID: {}", userId);

        Response res = notificationService.getNotificationsByUser(userId);

        log.info("Retrieved notifications for userId: {} | Status: {}", userId, res.getStatusCode());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get all pending notifications
    @GetMapping("/pending")
    public ResponseEntity<Response> getPendingNotifications() {
        log.info("Fetching all pending notifications");

        Response res = notificationService.getPendingNotifications();

        log.info("Retrieved pending notifications | Status: {}", res.getStatusCode());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Update notification status (e.g., SENT, FAILED)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Response> updateNotificationStatus(
            @PathVariable Long id,
            @RequestParam String newStatus) {

        log.info("Updating notification status | Notification ID: {}, New Status: {}", id, newStatus);

        Response res = notificationService.updateNotificationStatus(id, newStatus);

        log.info("Updated notification status | ID: {}, Status: {}, Message: {}", id, res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Delete Notification
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteNotification(@PathVariable Long id) {
        log.info(" Deleting notification with ID: {}", id);

        Response res = notificationService.deleteNotification(id);

        log.info("Deleted notification | ID: {}, Status: {}, Message: {}", id, res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
