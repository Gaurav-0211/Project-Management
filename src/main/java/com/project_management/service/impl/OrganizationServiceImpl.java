package com.project_management.service.impl;

import com.project_management.model.Organization;
import com.project_management.model.Response;
import com.project_management.repository.OrganizationRepository;
import com.project_management.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    // Create a new Organization
    @Override
    public Response createOrganization(String name, String timezone) {
        Response response = new Response();
        try {
            // Check if organization name already exists
            if (organizationRepository.findByName(name) != null) {
                response.setStatus("FAILED");
                response.setStatusCode(409);
                response.setMessage("Organization name already exists");
                response.setResponse_message("Duplicate organization name detected");
                return response;
            }

            Organization org = new Organization();
            org.setName(name);
            org.setTimezone(timezone);
            org.setCreatedAt(OffsetDateTime.now());

            Organization saved = organizationRepository.save(org);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Organization created successfully");
            response.setData(saved);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error creating organization");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Get all Organizations
    @Override
    public Response getAllOrganizations() {
        Response response = new Response();
        try {
            List<Organization> organizations = organizationRepository.findAll();

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Organizations retrieved successfully");
            response.setData(organizations);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error fetching organizations");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Get organization by ID
    @Override
    public Response getOrganizationById(Long id) {
        Response response = new Response();
        try {
            Optional<Organization> orgOpt = organizationRepository.findById(id);
            if (orgOpt.isEmpty()) {
                response.setStatus("FAILED");
                response.setStatusCode(404);
                response.setMessage("Organization not found");
                response.setResponse_message("No organization with ID: " + id);
                return response;
            }

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Organization fetched successfully");
            response.setData(orgOpt.get());
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error fetching organization");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Get organization by name
    @Override
    public Response getOrganizationByName(String name) {
        Response response = new Response();
        try {
            Organization org = organizationRepository.findByName(name);
            if (org == null) {
                response.setStatus("FAILED");
                response.setStatusCode(404);
                response.setMessage("Organization not found");
                response.setResponse_message("No organization with name: " + name);
                return response;
            }

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Organization fetched successfully");
            response.setData(org);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error fetching organization");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Update Organization
    @Override
    public Response updateOrganization(Long id, String newName, String timezone) {
        Response response = new Response();
        try {
            Optional<Organization> orgOpt = organizationRepository.findById(id);
            if (orgOpt.isEmpty()) {
                response.setStatus("FAILED");
                response.setStatusCode(404);
                response.setMessage("Organization not found");
                response.setResponse_message("Invalid organization ID: " + id);
                return response;
            }

            Organization org = orgOpt.get();
            if (newName != null && !newName.isBlank()) {
                // Check for duplicate names
                Organization existing = organizationRepository.findByName(newName);
                if (existing != null && !existing.getId().equals(id)) {
                    response.setStatus("FAILED");
                    response.setStatusCode(409);
                    response.setMessage("Another organization already exists with that name");
                    response.setResponse_message("Duplicate name detected");
                    return response;
                }
                org.setName(newName);
            }

            if (timezone != null) {
                org.setTimezone(timezone);
            }

            Organization updated = organizationRepository.save(org);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Organization updated successfully");
            response.setData(updated);
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error updating organization");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }

    // Delete Organization
    @Override
    public Response deleteOrganization(Long id) {
        Response response = new Response();
        try {
            Optional<Organization> orgOpt = organizationRepository.findById(id);
            if (orgOpt.isEmpty()) {
                response.setStatus("FAILED");
                response.setStatusCode(404);
                response.setMessage("Organization not found");
                response.setResponse_message("No organization with ID: " + id);
                return response;
            }

            organizationRepository.deleteById(id);

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Organization deleted successfully");
            response.setResponse_message("Process execution success");

        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Error deleting organization");
            response.setResponse_message(e.getMessage());
        }
        return response;
    }
}
