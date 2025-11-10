package com.project_management.controller;

import com.project_management.exception.NoDataExist;
import com.project_management.model.Response;
import com.project_management.model.User;
import com.project_management.repository.UserRepository;
import com.project_management.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Response response;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestParam String email, @RequestParam String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            User user = this.userRepository.findByEmail(email)
                    .orElseThrow(() -> new NoDataExist("Invalid email"));

            String token = jwtTokenUtil.generateToken(user.getEmail(), user.getRole().name());

            response.setStatus("SUCCESS");
            response.setStatusCode(200);
            response.setMessage("Login successful");
            response.setData("Bearer " + token);
            response.setResponse_message("Process execution success");

        } catch (BadCredentialsException e) {
            response.setStatus("FAILED");
            response.setStatusCode(401);
            response.setMessage("Invalid credentials");
            response.setResponse_message("Authentication failed");
        } catch (Exception e) {
            response.setStatus("FAILED");
            response.setStatusCode(500);
            response.setMessage("Login failed");
            response.setResponse_message(e.getMessage());
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
