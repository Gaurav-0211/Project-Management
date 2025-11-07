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
@Table(name = "user_notification_preferences", indexes = {
        @Index(name = "idx_pref_user", columnList = "user_id")
})
public class UserNotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user this preference belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    // Channel-specific preference (EMAIL, PUSH, SMS)
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private NotificationChannel channel;

    // If true, send instant for this channel. If false, use digest/batching for this channel.
    @Column(nullable=false)
    private boolean instant = true;

    // If using digest, the digest frequency in minutes (e.g., 60 for hourly)
    private Integer digestFrequencyMinutes;

    // Whether the user wants a given notification type (e.g., task_updates, comments) - generic string for POC
    @Column(nullable = false)
    private String preferenceType; // e.g., "TASK_UPDATE", "TASK_ASSIGNMENT", "KPI_ALERT"

    @Column(nullable = false)
    private OffsetDateTime createdAt;

}
