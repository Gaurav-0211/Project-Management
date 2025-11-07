package com.project_management.service.impl;

import com.project_management.model.Device;
import com.project_management.model.Response;
import com.project_management.model.User;
import com.project_management.repository.DeviceRepository;
import com.project_management.repository.UserRepository;
import com.project_management.service.DeviceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private  DeviceRepository deviceRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private Response response;

    @Override
    public Response registerDevice(Long userId, String pushToken, String platform) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

            // Check if token already exists
            Device existing = deviceRepository.findByPushToken(pushToken).orElse(null);
            if (existing != null) {
                existing.setLastSeenAt(OffsetDateTime.now());
                existing.setPlatform(platform);
                deviceRepository.save(existing);

                response.setStatus("SUCCESS");
                response.setStatusCode(200);
                response.setMessage("Device already registered, updated lastSeenAt");
                response.setData(existing);
                response.setResponse_message("Process execution success");
                return response;
            }

            Device device = new Device();
            device.setUser(user);
            device.setPushToken(pushToken);
            device.setPlatform(platform);
            device.setLastSeenAt(OffsetDateTime.now());
            Device saved = deviceRepository.save(device);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Device registered successfully");
            response.setData(saved);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error registering device");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateLastSeen(String pushToken) {
        try {
            Device device = deviceRepository.findByPushToken(pushToken)
                    .orElseThrow(() -> new IllegalArgumentException("Device not found with token: " + pushToken));

            device.setLastSeenAt(OffsetDateTime.now());
            Device updated = deviceRepository.save(device);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Device lastSeenAt updated successfully");
            response.setData(updated);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error updating device lastSeenAt");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getDevicesByUser(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

            List<Device> devices = deviceRepository.findByUser(user);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Devices fetched successfully");
            response.setData(devices);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error fetching devices");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteDevice(String pushToken) {
        try {
            Device device = deviceRepository.findByPushToken(pushToken)
                    .orElseThrow(() -> new IllegalArgumentException("Device not found with token: " + pushToken));

            deviceRepository.delete(device);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Device deleted successfully");
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error deleting device");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }
}
