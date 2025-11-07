package com.project_management.model;

public enum NotificationStatus {
    PENDING,       // created but not processed yet
    IN_PROGRESS,   // being handled
    SENT,          // delivered successfully to at least one channel
    FAILED,        // all channels failed (final)
    PARTIAL        // some channels succeeded, others failed
}
