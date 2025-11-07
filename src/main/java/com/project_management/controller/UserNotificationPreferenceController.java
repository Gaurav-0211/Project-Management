package com.project_management.controller;

import com.project_management.model.Response;
import com.project_management.service.UserNotificationPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/preferences")
public class UserNotificationPreferenceController {

    @Autowired
    private UserNotificationPreferenceService preferenceService;

    // Create new user preference
    @PostMapping
    public ResponseEntity<Response> createPreference(
            @RequestParam Long userId,
            @RequestParam String channel,
            @RequestParam(defaultValue = "true") boolean instant,
            @RequestParam(required = false) Integer digestFrequencyMinutes,
            @RequestParam String preferenceType) {

        Response res = preferenceService.createPreference(userId, channel, instant, digestFrequencyMinutes, preferenceType);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get preferences for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response> getPreferencesByUser(@PathVariable Long userId) {
        Response res = preferenceService.getPreferencesByUser(userId);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Update a user preference
    @PutMapping("/{prefId}")
    public ResponseEntity<Response> updatePreference(
            @PathVariable Long prefId,
            @RequestParam boolean instant,
            @RequestParam(required = false) Integer digestFrequencyMinutes) {

        Response res = preferenceService.updatePreference(prefId, instant, digestFrequencyMinutes);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Delete a user preference
    @DeleteMapping("/{prefId}")
    public ResponseEntity<Response> deletePreference(@PathVariable Long prefId) {
        Response res = preferenceService.deletePreference(prefId);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
