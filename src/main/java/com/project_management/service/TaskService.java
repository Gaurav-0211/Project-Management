package com.project_management.service;


import com.project_management.model.Response;

import java.time.OffsetDateTime;
import java.util.List;

public interface TaskService {

    Response createTask(Long orgId, String title, String description, OffsetDateTime dueAt);

    Response getTaskById(Long id);

    Response getAllTasks();

    Response getTasksByOrganization(Long orgId);

    Response updateTask(Long id, String title, String description, OffsetDateTime dueAt, String status);

    Response deleteTask(Long id);

    Response updateTaskStatus(Long taskId, String newStatus);

    Response assignUsersToTask(Long taskId, List<Long> userIds);

    Response addWatchersToTask(Long taskId, List<Long> watcherIds);

    Response getTasksByStatus(String status);
}
