package com.service.auth_service.service;

import com.service.auth_service.dto.AuthResponse;
import com.service.auth_service.dto.LoginRequest;
import com.service.auth_service.dto.RefreshRequest;
import com.service.auth_service.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshRequest request);
}
