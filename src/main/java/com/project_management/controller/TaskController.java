package com.project_management.controller;

import com.project_management.model.Response;
import com.project_management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;


    // Create a new Task
    @PostMapping
    public ResponseEntity<Response> createTask(
            @RequestParam Long orgId,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dueAt) {

        Response res = taskService.createTask(orgId, title, description, dueAt);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<Response> getAllTasks() {
        Response res = taskService.getAllTasks();
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTaskById(@PathVariable Long id) {
        Response res = taskService.getTaskById(id);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get tasks by Organization ID
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<Response> getTasksByOrganization(@PathVariable Long orgId) {
        Response res = taskService.getTasksByOrganization(orgId);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get tasks by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<Response> getTasksByStatus(@PathVariable String status) {
        Response res = taskService.getTasksByStatus(status);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Update task details
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateTask(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dueAt,
            @RequestParam(required = false) String status) {

        Response res = taskService.updateTask(id, title, description, dueAt, status);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Update task status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Response> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam String newStatus) {

        Response res = taskService.updateTaskStatus(id, newStatus);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Assign users to a task
    @PostMapping("/{id}/assign")
    public ResponseEntity<Response> assignUsersToTask(
            @PathVariable Long id,
            @RequestBody List<Long> userIds) {

        Response res = taskService.assignUsersToTask(id, userIds);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Add watchers to a task
    @PostMapping("/{id}/watchers")
    public ResponseEntity<Response> addWatchersToTask(
            @PathVariable Long id,
            @RequestBody List<Long> watcherIds) {

        Response res = taskService.addWatchersToTask(id, watcherIds);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteTask(@PathVariable Long id) {
        Response res = taskService.deleteTask(id);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
