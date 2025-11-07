package com.project_management.repository;


import com.project_management.model.NotificationChannel;
import com.project_management.model.User;
import com.project_management.model.UserNotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationPreferenceRepository extends JpaRepository<UserNotificationPreference, Long> {

    List<UserNotificationPreference> findByUser(User user);

    List<UserNotificationPreference> findByUserAndChannel(User user, NotificationChannel channel);
}
