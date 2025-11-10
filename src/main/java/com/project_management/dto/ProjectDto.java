package com.project_management.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private Long organizationId;
    private String organizationName;
    private Long ownerId;
    private String ownerName;
    private Set<Long> memberIds;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
