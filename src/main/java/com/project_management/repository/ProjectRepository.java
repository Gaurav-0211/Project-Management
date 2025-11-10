package com.project_management.repository;

import com.project_management.model.Organization;
import com.project_management.model.Project;
import com.project_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOrganization(Organization organization);

    List<Project> findByOwner(User owner);

    List<Project> findByMembersContaining(User member);

    boolean existsByNameAndOrganization(String name, Organization organization);
}
