package com.project_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "idx_notifications_status_priority", columnList = "status, priority"),
        @Index(name = "idx_notifications_target_user", columnList = "targetUserId"),
        @Index(name = "idx_notifications_scheduled", columnList = "scheduledAt")
})
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The organization for scoping/multitenancy
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    // free text title and body
    @Column(nullable=false)
    private String title;

    @Column(length = 4000)
    private String body;

    // priority (URGENT etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private NotificationPriority priority = NotificationPriority.NORMAL;

    // current overall status
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private NotificationStatus status = NotificationStatus.PENDING;

    // Which user this notification is for (for per-user notifications)
    @Column(name = "targetUserId")
    private Long targetUserId;

    // when the notification should be processed (useful for digests/batching)
    @Column
    private OffsetDateTime scheduledAt;

    @Column(nullable=false)
    private OffsetDateTime createdAt;

    // optimistic locking
    @Version
    private Long version;

    // delivery attempts
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationDeliveryAttempt> deliveryAttempts = new ArrayList<>();


    // helper convenience
    public void addAttempt(NotificationDeliveryAttempt attempt) {
        attempt.setNotification(this);
        this.deliveryAttempts.add(attempt);
    }

}
