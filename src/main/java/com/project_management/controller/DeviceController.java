package com.project_management.controller;

import com.project_management.model.Response;
import com.project_management.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // Register Device
    @PostMapping("/register")
    public ResponseEntity<Response> registerDevice(
            @RequestParam Long userId,
            @RequestParam String pushToken,
            @RequestParam(required = false) String platform) {

        log.info("Register device in controller");
        Response res = deviceService.registerDevice(userId, pushToken, platform);
        return ResponseEntity.ok(res);
    }

    // Update lastSeenAt
    @PatchMapping("/seen")
    public ResponseEntity<Response> updateLastSeen(@RequestParam String pushToken) {
        log.info("Device seen in controller");
        Response res = deviceService.updateLastSeen(pushToken);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get devices for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response> getDevicesByUser(@PathVariable Long userId) {
        log.info("get device by user in controller");
        Response res = deviceService.getDevicesByUser(userId);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Delete device by token
    @DeleteMapping
    public ResponseEntity<Response> deleteDevice(@RequestParam String pushToken) {
        log.info("delete device in controller");
        Response res = deviceService.deleteDevice(pushToken);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
