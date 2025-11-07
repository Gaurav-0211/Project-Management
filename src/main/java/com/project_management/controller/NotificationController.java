package com.project_management.controller;

import com.project_management.model.Response;
import com.project_management.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;


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

        Response res = notificationService.createNotification(orgId, targetUserId, title, body, priority, scheduledAt);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get all notifications
    @GetMapping
    public ResponseEntity<Response> getAllNotifications() {
        Response res = notificationService.getAllNotifications();
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getNotificationById(@PathVariable Long id) {
        Response res = notificationService.getNotificationById(id);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get notifications by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response> getNotificationsByUser(@PathVariable Long userId) {
        Response res = notificationService.getNotificationsByUser(userId);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get all pending notifications
    @GetMapping("/pending")
    public ResponseEntity<Response> getPendingNotifications() {
        Response res = notificationService.getPendingNotifications();
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Update notification status (e.g., SENT, FAILED)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Response> updateNotificationStatus(
            @PathVariable Long id,
            @RequestParam String newStatus) {

        Response res = notificationService.updateNotificationStatus(id, newStatus);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Delete Notification
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteNotification(@PathVariable Long id) {
        Response res = notificationService.deleteNotification(id);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
