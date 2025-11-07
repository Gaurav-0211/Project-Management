package com.project_management.controller;

import com.project_management.dto.UserDto;
import com.project_management.model.Organization;
import com.project_management.model.Response;
import com.project_management.model.Role;
import com.project_management.repository.OrganizationRepository;
import com.project_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationRepository organizationRepository;

    // Create new user
    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody UserDto dto) {
        Response response = this.userService.createUser(dto);
        return ResponseEntity.ok(response);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Response> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // Get users by organization
    @GetMapping("/organization/{orgName}")
    public ResponseEntity<Response> getUsersByOrganization(@PathVariable String orgName) {
        return ResponseEntity.ok(userService.getUsersByOrganization(orgName));
    }

    // Get users by role within organization
    @GetMapping("/organization/{orgId}/role/{role}")
    public ResponseEntity<Response> getUsersByRole(@PathVariable Long orgId, @PathVariable Role role) {
        Optional<Organization> orgOpt = organizationRepository.findById(orgId);
        if (orgOpt.isEmpty()) {
            Response response = new Response();
            response.setStatus("FAILED");
            response.setMessage("Organization Id not found");
            response.setStatusCode(404);
            response.setData(null);
            response.setResponse_message("Process execution success");
            return ResponseEntity.ok(response);
        }
        Response response = userService.getUsersByRole(role, orgOpt.get());
        return ResponseEntity.ok(response);
    }

    // Update existing user
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody UserDto updatedUser) {
        Response response = this.userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(response);
    }

    // Update user role
    @PatchMapping("/{id}/role")
    public ResponseEntity<Response> updateUserRole(@PathVariable Long id, @RequestParam Role newRole) {
        return ResponseEntity.ok(this.userService.updateUserRole(id, newRole));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.deleteUser(id));
    }

    // Check if email already exists
    @GetMapping("/exists")
    public ResponseEntity<?> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
