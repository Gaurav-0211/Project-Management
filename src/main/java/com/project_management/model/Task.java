package com.project_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "idx_task_org", columnList = "organization_id"),
        @Index(name = "idx_task_status", columnList = "status")
})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(length = 4000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    @Column
    private OffsetDateTime dueAt;

    @Column(nullable=false)
    private OffsetDateTime createdAt;

    @Column
    private String status; // e.g., OPEN, IN_PROGRESS, COMPLETED

    // who is assigned (simple many-to-many so tasks can have multiple assignees)
    @ManyToMany
    @JoinTable(name = "task_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> assignees = new HashSet<>();

    // watchers who receive notifications
    @ManyToMany
    @JoinTable(name = "task_watchers",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> watchers = new HashSet<>();

}
