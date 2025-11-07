package com.project_management.repository;


import com.project_management.model.Device;
import com.project_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByUser(User user);

    Optional<Device> findByPushToken(String pushToken);
}
