package com.project_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notification_delivery_attempts", indexes = {
        @Index(name = "idx_attempt_notification", columnList = "notification_id"),
        @Index(name = "idx_attempt_status", columnList = "status")
})
public class NotificationDeliveryAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // the parent notification
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    @Column
    private String destination; // email, phone number, push token (for logging)

    @Column(nullable=false)
    private OffsetDateTime attemptedAt;

    @Column(nullable=false)
    private boolean success;

    @Column(length = 2000)
    private String errorMessage;

    @Column(nullable=false)
    private int attemptNumber;

    private String status;

}
