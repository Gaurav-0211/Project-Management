package com.project_management.controller;

import com.project_management.model.Response;
import com.project_management.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "*")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // Register Device
    @PostMapping("/register")
    public ResponseEntity<Response> registerDevice(
            @RequestParam Long userId,
            @RequestParam String pushToken,
            @RequestParam(required = false) String platform) {

        Response res = deviceService.registerDevice(userId, pushToken, platform);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Update lastSeenAt
    @PatchMapping("/seen")
    public ResponseEntity<Response> updateLastSeen(@RequestParam String pushToken) {
        Response res = deviceService.updateLastSeen(pushToken);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get devices for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response> getDevicesByUser(@PathVariable Long userId) {
        Response res = deviceService.getDevicesByUser(userId);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Delete device by token
    @DeleteMapping
    public ResponseEntity<Response> deleteDevice(@RequestParam String pushToken) {
        Response res = deviceService.deleteDevice(pushToken);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
