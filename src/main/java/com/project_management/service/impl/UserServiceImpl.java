package com.project_management.service.impl;

import com.project_management.dto.UserDto;
import com.project_management.exception.NoDataExist;
import com.project_management.model.Organization;
import com.project_management.model.Response;
import com.project_management.model.Role;
import com.project_management.model.User;
import com.project_management.repository.UserRepository;
import com.project_management.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Response response;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Response createUser(UserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new NoDataExist("Email already exists: " + dto.getEmail());
        }
        User user = this.mapper.map(dto, User.class);
        user.setCreatedAt(OffsetDateTime.now());
        if (dto.getRole() == null) {
            user.setRole(Role.EMPLOYEE);
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved =  userRepository.save(user);

        response.setStatus("SUCCESS");
        response.setStatusCode(201);
        response.setMessage("User Created successfully");
        response.setData(this.mapper.map(saved, UserDto.class));
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response getUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(()-> new NoDataExist("No User fetched with given id"));

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("User fetched successfully");
        response.setData(this.mapper.map(user, UserDto.class));
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response getUserByEmail(String email) {
        User user =  userRepository.findByEmail(email)
                .orElseThrow(()-> new NoDataExist("No User fetched with given email"));

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("User fetched successfully");
        response.setData(this.mapper.map(user, UserDto.class));
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response getAllUsers() {
        List<User> users =  userRepository.findAll();

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("All User fetched successfully");
        response.setData(users.stream().map((user)-> this.mapper.map(user, UserDto.class)).collect(Collectors.toList()));
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response getUsersByOrganization(String organization) {
        List<User> users =  userRepository.findByOrganizationName(organization);

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("All User fetched successfully");
        response.setData(users.stream().map((user)-> this.mapper.map(user, UserDto.class)).collect(Collectors.toList()));
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response getUsersByRole(Role role, Organization organization) {
        List<User> users =  userRepository.findByRoleAndOrganization(role, organization);

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("All User fetched successfully");
        response.setData(users.stream().map((user)-> this.mapper.map(user, UserDto.class)).collect(Collectors.toList()));
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response updateUser(Long id, UserDto dto) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new NoDataExist("No User exist with given Id"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = this.userRepository.save(user);

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("User Updated successfully");
        response.setData(this.mapper.map(user, UserDto.class));
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response deleteUser(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new NoDataExist("No user found with given Id"));

        this.userRepository.delete(user);
        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("User Deleted successfully");
        response.setData(null);
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response updateUserRole(Long id, Role newRole) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new NoDataExist("User not found with ID: " + id));
        user.setRole(newRole);
        this.userRepository.save(user);

        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        response.setMessage("User Role updated successfully");
        response.setData(this.mapper.map(user,UserDto.class));
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
