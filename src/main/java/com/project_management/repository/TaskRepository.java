package com.project_management.repository;


import com.project_management.model.Organization;
import com.project_management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByOrganization(Organization organization);

    List<Task> findByStatus(String status);
}
