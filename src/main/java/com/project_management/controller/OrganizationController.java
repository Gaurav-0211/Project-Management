package com.project_management.controller;

import com.project_management.model.Response;
import com.project_management.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    // Create Organization
    @PostMapping
    public ResponseEntity<Response> createOrganization(
            @RequestParam String name,
            @RequestParam(required = false) String timezone) {

        log.info("Creating new organization with name: '{}' and timezone: '{}'", name, timezone);

        Response res = organizationService.createOrganization(name, timezone);

        log.info("Organization creation completed. Status: {}, Message: {}", res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get All Organizations
    @GetMapping
    public ResponseEntity<Response> getAllOrganizations() {
        log.info("Fetching all organizations");

        Response res = organizationService.getAllOrganizations();

        log.info("Fetched all organizations. Status: {}, Count: {}",
                res.getStatusCode(),
                (res.getData() instanceof java.util.Collection) ? ((java.util.Collection<?>) res.getData()).size() : 0);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get Organization by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrganizationById(@PathVariable Long id) {
        log.info("Fetching organization by ID: {}", id);

        Response res = organizationService.getOrganizationById(id);

        log.info("Fetch by ID completed. ID: {}, Status: {}, Message: {}", id, res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get Organization by Name
    @GetMapping("/name/{name}")
    public ResponseEntity<Response> getOrganizationByName(@PathVariable String name) {
        log.info("Fetching organization by name: '{}'", name);

        Response res = organizationService.getOrganizationByName(name);

        log.info("Fetch by name completed. Name: '{}', Status: {}, Message: {}", name, res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Update Organization (name and/or timezone)
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateOrganization(
            @PathVariable Long id,
            @RequestParam(required = false) String newName,
            @RequestParam(required = false) String timezone) {

        log.info("Updating organization with ID: {}. New name: '{}', New timezone: '{}'", id, newName, timezone);

        Response res = organizationService.updateOrganization(id, newName, timezone);

        log.info("Organization update completed. ID: {}, Status: {}, Message: {}", id, res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Delete Organization
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteOrganization(@PathVariable Long id) {
        log.info("Deleting organization with ID: {}", id);

        Response res = organizationService.deleteOrganization(id);

        log.info("Organization deletion completed. ID: {}, Status: {}, Message: {}", id, res.getStatusCode(), res.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
