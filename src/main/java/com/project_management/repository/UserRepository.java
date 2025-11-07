package com.project_management.repository;


import com.project_management.model.Organization;
import com.project_management.model.Role;
import com.project_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByOrganizationName(String organization);

    List<User> findByRoleAndOrganization(Role role, Organization organization);

    boolean existsByEmail(String email);
}
