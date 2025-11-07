package com.project_management.service.impl;

import com.project_management.exception.NoDataExist;
import com.project_management.model.Organization;
import com.project_management.model.Response;
import com.project_management.model.Task;
import com.project_management.model.User;
import com.project_management.repository.OrganizationRepository;
import com.project_management.repository.TaskRepository;
import com.project_management.repository.UserRepository;
import com.project_management.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private Response response;

    // Create Task
    @Override
    public Response createTask(Long orgId, String title, String description, OffsetDateTime dueAt) {
        try {
            Organization org = organizationRepository.findById(orgId)
                    .orElseThrow(() -> new NoDataExist("Invalid organization ID"));

            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setOrganization(org);
            task.setStatus("PENDING");
            task.setCreatedAt(OffsetDateTime.now());
            task.setDueAt(dueAt);

            Task saved = taskRepository.save(task);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Task created successfully");
            response.setData(saved);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error creating task");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Get Task by ID
    @Override
    public Response getTaskById(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);

        if (taskOpt.isPresent()) {
            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Task fetched successfully");
            response.setData(taskOpt.get());
            response.setResponse_message("Process execution success");
        } else {
            response.setStatus("FAILED");
            response.setStatusCode(404);
            response.setMessage("Task not found");
            response.setResponse_message("No task found with id: " + id);
        }

        return response;
    }

    // Get All Tasks
    @Override
    public Response getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("All tasks retrieved successfully");
        response.setData(tasks);
        response.setResponse_message("Process execution success");
        return response;
    }

    // Get Tasks by Organization
    @Override
    public Response getTasksByOrganization(Long orgId) {
        try {
            Organization org = organizationRepository.findById(orgId)
                    .orElseThrow(() -> new NoDataExist("Organization not found"));

            List<Task> tasks = taskRepository.findByOrganization(org);
            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Tasks fetched successfully");
            response.setData(tasks);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(404);
            response.setMessage("Error fetching tasks by organization");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Update Task
    @Override
    public Response updateTask(Long id, String title, String description, OffsetDateTime dueAt, String status) {
        try {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new NoDataExist("Task not found with id: " + id));

            if (title != null) task.setTitle(title);
            if (description != null) task.setDescription(description);
            if (dueAt != null) task.setDueAt(dueAt);
            if (status != null) task.setStatus(status);

            Task updated = taskRepository.save(task);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Task updated successfully");
            response.setData(updated);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Task update failed");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Delete Task
    @Override
    public Response deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            response.setStatus("FAILED");
            response.setStatusCode(404);
            response.setMessage("Task not found");
            response.setResponse_message("No task with ID: " + id);
            return response;
        }

        taskRepository.deleteById(id);
        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("Task deleted successfully");
        response.setResponse_message("Process execution success");
        return response;
    }

    // Update Task Status
    @Override
    public Response updateTaskStatus(Long taskId, String newStatus) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new NoDataExist("Task not found with id: " + taskId));

            task.setStatus(newStatus);
            Task updated = taskRepository.save(task);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Task status updated successfully");
            response.setData(updated);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Task status update failed");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Assign Users to Task
    @Override
    public Response assignUsersToTask(Long taskId, List<Long> userIds) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new NoDataExist("Task not found with id: " + taskId));

            List<User> users = userRepository.findAllById(userIds);
            task.getAssignees().addAll(users);

            Task updated = taskRepository.save(task);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Users assigned successfully");
            response.setData(updated);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error assigning users");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // 🟣 Add Watchers to Task
    @Override
    public Response addWatchersToTask(Long taskId, List<Long> watcherIds) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new NoDataExist("Task not found with id: " + taskId));

            List<User> watchers = userRepository.findAllById(watcherIds);
            task.getWatchers().addAll(watchers);

            Task updated = taskRepository.save(task);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Watchers added successfully");
            response.setData(updated);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error adding watchers");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Get Tasks by Status
    @Override
    public Response getTasksByStatus(String status) {
        List<Task> tasks = taskRepository.findByStatus(status);

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("Tasks fetched successfully by status");
        response.setData(tasks);
        response.setResponse_message("Process execution success");
        return response;
    }
}
