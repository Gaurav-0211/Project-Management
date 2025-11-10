package com.project_management.service;


import com.project_management.model.Response;

import java.util.List;

public interface ProjectService {

    Response createProject(Long orgId, Long ownerId, String name, String description, List<Long> memberIds);

    Response getProjectById(Long id);

    Response getProjectsByOrganization(Long orgId);

    Response getProjectsByOwner(Long ownerId);

    Response updateProject(Long id, String name, String description, List<Long> memberIds);

    Response deleteProject(Long id);
}
