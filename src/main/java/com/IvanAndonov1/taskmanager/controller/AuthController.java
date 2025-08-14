package com.IvanAndonov1.taskmanager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IvanAndonov1.taskmanager.model.Role;
import com.IvanAndonov1.taskmanager.model.User;
import com.IvanAndonov1.taskmanager.repository.UserRepository;
import com.IvanAndonov1.taskmanager.security.JwtService;
import com.IvanAndonov1.taskmanager.security.UserDetailsServiceImpl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        log.info("Attempting to register user: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Username '{}' already taken", request.getUsername());
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        log.info("User '{}' registered successfully", request.getUsername());

        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest request) {
        log.info("User '{}' attempting to log in", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn("Login failed: user '{}' not found", request.getUsername());
                    return new RuntimeException("User not found");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Invalid password attempt for user '{}'", request.getUsername());
            throw new RuntimeException("Invalid credentials");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        log.info("User '{}' logged in successfully", request.getUsername());
        return Map.of("token", token);
    }

    @Data
    static class AuthRequest {
        private String username;
        private String password;
    }
}
