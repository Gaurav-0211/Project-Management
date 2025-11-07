package com.project_management.service.impl;

import com.project_management.exception.IllegalArgumentException;
import com.project_management.model.*;
import com.project_management.repository.NotificationRepository;
import com.project_management.repository.OrganizationRepository;
import com.project_management.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private  NotificationRepository notificationRepository;

    @Autowired
    private  OrganizationRepository organizationRepository;

    @Autowired
    private Response response;



    @Override
    public Response createNotification(Long orgId, Long targetUserId, String title, String body,
                                       String priority, OffsetDateTime scheduledAt) {
        try {
            Organization org = organizationRepository.findById(orgId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found with ID: " + orgId));

            Notification notification = new Notification();
            notification.setOrganization(org);
            notification.setTitle(title);
            notification.setBody(body);
            notification.setTargetUserId(targetUserId);
            notification.setPriority(NotificationPriority.valueOf(priority.toUpperCase()));
            notification.setStatus(NotificationStatus.PENDING);
            notification.setScheduledAt(scheduledAt);
            notification.setCreatedAt(OffsetDateTime.now());

            Notification saved = notificationRepository.save(notification);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Notification created successfully");
            response.setData(saved);
            response.setResponse_message("Process execution success");

        } catch (IllegalArgumentException e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Invalid request");
            response.setResponse_message(e.getMessage());
        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error creating notification");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getNotificationById(Long id) {
        Optional<Notification> notificationOpt = notificationRepository.findById(id);

        if (notificationOpt.isPresent()) {
            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Notification fetched successfully");
            response.setData(notificationOpt.get());
            response.setResponse_message("Process execution success");
        } else {
            response.setStatus("FAILED");
            response.setStatusCode(404);
            response.setMessage("Notification not found");
            response.setResponse_message("No notification with ID: " + id);
        }

        return response;
    }

    @Override
    public Response getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("All notifications retrieved successfully");
        response.setData(notifications);
        response.setResponse_message("Process execution success");
        return response;
    }

    @Override
    public Response getNotificationsByUser(Long userId) {
        Response response = new Response();
        List<Notification> notifications = notificationRepository.findAll()
                .stream()
                .filter(n -> n.getTargetUserId() != null && n.getTargetUserId().equals(userId))
                .toList();

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("User notifications retrieved successfully");
        response.setData(notifications);
        response.setResponse_message("Process execution success");
        return response;
    }

    @Override
    public Response getPendingNotifications() {
        List<Notification> notifications =
                notificationRepository.findByStatusAndPriority(NotificationStatus.PENDING, NotificationPriority.NORMAL);

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("Pending notifications fetched successfully");
        response.setData(notifications);
        response.setResponse_message("Process execution success");
        return response;
    }

    @Override
    public Response updateNotificationStatus(Long id, String newStatus) {
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Notification not found with ID: " + id));

            notification.setStatus(NotificationStatus.valueOf(newStatus.toUpperCase()));
            Notification updated = notificationRepository.save(notification);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Notification status updated successfully");
            response.setData(updated);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error updating notification status");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteNotification(Long id) {
        try {
            if (!notificationRepository.existsById(id)) {
                response.setStatus("FAILED");
                response.setStatusCode(404);
                response.setMessage("Notification not found");
                response.setResponse_message("No notification with ID: " + id);
                return response;
            }

            notificationRepository.deleteById(id);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Notification deleted successfully");
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error deleting notification");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }
}
