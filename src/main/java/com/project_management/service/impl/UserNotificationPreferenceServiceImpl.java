package com.project_management.service.impl;

import com.project_management.model.*;
import com.project_management.repository.UserNotificationPreferenceRepository;
import com.project_management.repository.UserRepository;
import com.project_management.service.UserNotificationPreferenceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class UserNotificationPreferenceServiceImpl implements UserNotificationPreferenceService {

    @Autowired
    private  UserNotificationPreferenceRepository preferenceRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private Response response;



    @Override
    public Response createPreference(Long userId, String channel, boolean instant,
                                     Integer digestFrequencyMinutes, String preferenceType) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

            UserNotificationPreference pref = new UserNotificationPreference();
            pref.setUser(user);
            pref.setChannel(NotificationChannel.valueOf(channel.toUpperCase()));
            pref.setInstant(instant);
            pref.setDigestFrequencyMinutes(digestFrequencyMinutes);
            pref.setPreferenceType(preferenceType.toUpperCase());
            pref.setCreatedAt(OffsetDateTime.now());

            UserNotificationPreference saved = preferenceRepository.save(pref);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("User notification preference created successfully");
            response.setData(saved);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error creating user preference");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getPreferencesByUser(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

            List<UserNotificationPreference> prefs = preferenceRepository.findByUser(user);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("User preferences fetched successfully");
            response.setData(prefs);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error fetching user preferences");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response updatePreference(Long prefId, boolean instant, Integer digestFrequencyMinutes) {
        try {
            UserNotificationPreference pref = preferenceRepository.findById(prefId)
                    .orElseThrow(() -> new IllegalArgumentException("Preference not found with ID: " + prefId));

            pref.setInstant(instant);
            if (digestFrequencyMinutes != null) {
                pref.setDigestFrequencyMinutes(digestFrequencyMinutes);
            }

            UserNotificationPreference updated = preferenceRepository.save(pref);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Preference updated successfully");
            response.setData(updated);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(400);
            response.setMessage("Error updating preference");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response deletePreference(Long prefId) {
        try {
            if (!preferenceRepository.existsById(prefId)) {
                response.setStatus("FAILED");
                response.setStatusCode(404);
                response.setMessage("Preference not found");
                response.setResponse_message("No preference with ID: " + prefId);
                return response;
            }

            preferenceRepository.deleteById(prefId);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Preference deleted successfully");
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error deleting preference");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }
}
