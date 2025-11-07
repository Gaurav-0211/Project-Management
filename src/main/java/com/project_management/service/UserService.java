package com.project_management.service;

import com.project_management.dto.UserDto;
import com.project_management.model.Organization;
import com.project_management.model.Response;
import com.project_management.model.Role;

public interface UserService {

    Response createUser(UserDto dto);

    Response getUserById(Long id);

    Response getUserByEmail(String email);

    Response getAllUsers();

    Response getUsersByOrganization(String organization);

    Response getUsersByRole(Role role, Organization organization);

    Response updateUser(Long id, UserDto updatedUser);

    Response deleteUser(Long id);

    Response updateUserRole(Long id, Role newRole);

    boolean existsByEmail(String email);
}
