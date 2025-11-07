package com.project_management.service;


import com.project_management.model.Response;

public interface DeviceService {

    Response registerDevice(Long userId, String pushToken, String platform);

    Response updateLastSeen(String pushToken);

    Response getDevicesByUser(Long userId);

    Response deleteDevice(String pushToken);
}
