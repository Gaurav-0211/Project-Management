package com.project_management.service.impl;

import com.project_management.dto.ProjectDto;
import com.project_management.model.Organization;
import com.project_management.model.Project;
import com.project_management.model.Response;
import com.project_management.model.User;
import com.project_management.repository.OrganizationRepository;
import com.project_management.repository.ProjectRepository;
import com.project_management.repository.UserRepository;
import com.project_management.service.ProjectService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Response response;



    @Override
    public Response createProject(Long orgId, Long ownerId, String name, String description, List<Long> memberIds) {
        try {
            Organization org = organizationRepository.findById(orgId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found with ID: " + orgId));

            User owner = userRepository.findById(ownerId)
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found with ID: " + ownerId));

            if (projectRepository.existsByNameAndOrganization(name, org)) {
                response.setStatus("FAILED");
                response.setStatusCode(409);
                response.setMessage("A project with this name already exists in this organization");
                response.setResponse_message("Duplicate project name");
                return response;
            }

            Project project = new Project();
            project.setName(name);
            project.setDescription(description);
            project.setOrganization(org);
            project.setOwner(owner);

            if (memberIds != null && !memberIds.isEmpty()) {
                Set<User> members = new HashSet<>(userRepository.findAllById(memberIds));
                project.setMembers(members);
            }

            Project saved = projectRepository.save(project);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Project created successfully");
            response.setData(this.mapper.map(saved, ProjectDto.class));
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error creating project");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getProjectById(Long id) {
        try {
            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + id));

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Project fetched successfully");
            response.setData(convertToDto(project));
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(404);
            response.setMessage("Error fetching project");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getProjectsByOrganization(Long orgId) {
        try {
            Organization org = organizationRepository.findById(orgId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found with ID: " + orgId));

            List<Project> projects = projectRepository.findByOrganization(org);
            List<ProjectDto> dtoList = projects.stream().map(this::convertToDto).collect(Collectors.toList());

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Projects fetched successfully");
            response.setData(dtoList);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(404);
            response.setMessage("Error fetching projects by organization");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getProjectsByOwner(Long ownerId) {
        try {
            User owner = userRepository.findById(ownerId)
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found with ID: " + ownerId));

            List<Project> projects = projectRepository.findByOwner(owner);
            List<ProjectDto> dtoList = projects.stream().map(this::convertToDto).collect(Collectors.toList());

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Projects fetched successfully for owner");
            response.setData(dtoList);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(404);
            response.setMessage("Error fetching projects for owner");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateProject(Long id, String name, String description, List<Long> memberIds) {
        try {
            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + id));

            if (name != null && !name.isBlank()) project.setName(name);
            if (description != null) project.setDescription(description);

            if (memberIds != null) {
                Set<User> members = new HashSet<>(userRepository.findAllById(memberIds));
                project.setMembers(members);
            }

            Project updated = projectRepository.save(project);
            ProjectDto dto = convertToDto(updated);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Project updated successfully");
            response.setData(dto);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error updating project");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteProject(Long id) {
        try {
            if (!projectRepository.existsById(id)) {
                response.setStatus("FAILED");
                response.setStatusCode(404);
                response.setMessage("Project not found");
                response.setResponse_message("No project with ID: " + id);
                return response;
            }

            projectRepository.deleteById(id);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Project deleted successfully");
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error deleting project");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    private ProjectDto convertToDto(Project project) {
        ProjectDto dto = mapper.map(project, ProjectDto.class);
        dto.setOrganizationId(project.getOrganization().getId());
        dto.setOrganizationName(project.getOrganization().getName());
        dto.setOwnerId(project.getOwner().getId());
        dto.setOwnerName(project.getOwner().getName());
        dto.setMemberIds(project.getMembers().stream().map(User::getId).collect(Collectors.toSet()));
        return dto;
    }
}
