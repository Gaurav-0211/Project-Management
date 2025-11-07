package com.project_management.controller;

import com.project_management.model.Response;
import com.project_management.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

        Response res = organizationService.createOrganization(name, timezone);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get All Organizations
    @GetMapping
    public ResponseEntity<Response> getAllOrganizations() {
        Response res = organizationService.getAllOrganizations();
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get Organization by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrganizationById(@PathVariable Long id) {
        Response res = organizationService.getOrganizationById(id);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Get Organization by Name
    @GetMapping("/name/{name}")
    public ResponseEntity<Response> getOrganizationByName(@PathVariable String name) {
        Response res = organizationService.getOrganizationByName(name);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Update Organization (name and/or timezone)
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateOrganization(
            @PathVariable Long id,
            @RequestParam(required = false) String newName,
            @RequestParam(required = false) String timezone) {

        Response res = organizationService.updateOrganization(id, newName, timezone);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    // Delete Organization
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteOrganization(@PathVariable Long id) {
        Response res = organizationService.deleteOrganization(id);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
