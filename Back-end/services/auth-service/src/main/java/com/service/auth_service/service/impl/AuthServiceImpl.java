package com.service.auth_service.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.service.auth_service.dto.AuthResponse;
import com.service.auth_service.dto.LoginRequest;
import com.service.auth_service.dto.RefreshRequest;
import com.service.auth_service.dto.RegisterRequest;
import com.service.auth_service.dto.UserResponse;
import com.service.auth_service.entity.Role;
import com.service.auth_service.entity.User;
import com.service.auth_service.repository.UserRepository;
import com.service.auth_service.service.AuthService;
import com.service.auth_service.service.JwtService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Role role = request.getRole() != null ? request.getRole() : Role.PATIENT;

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);
        return buildAuthResponse(saved);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword());
        authenticationManager.authenticate(authentication);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse refresh(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        if (!jwtService.isTokenValid(refreshToken) || !jwtService.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.isEnabled());
        return new AuthResponse(
                newAccessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenMinutes(),
                userResponse);
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail(), user.getRole());
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.isEnabled());
        return new AuthResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenMinutes(),
                userResponse);
    }
}
