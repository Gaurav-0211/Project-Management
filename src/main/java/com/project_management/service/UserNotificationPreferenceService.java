package com.project_management.service;


import com.project_management.model.Response;

public interface UserNotificationPreferenceService {

    Response createPreference(Long userId, String channel, boolean instant,
                              Integer digestFrequencyMinutes, String preferenceType);

    Response getPreferencesByUser(Long userId);

    Response updatePreference(Long prefId, boolean instant, Integer digestFrequencyMinutes);

    Response deletePreference(Long prefId);
}
