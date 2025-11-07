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
@Table(name = "devices", indexes = {
        @Index(name = "idx_device_token", columnList = "pushToken")
})
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    private User user;

    @Column(nullable=false, unique=true)
    private String pushToken; // device token for push providers

    @Column
    private String platform; // "ios", "android", "web"

    @Column(nullable=false)
    private OffsetDateTime lastSeenAt;


}
