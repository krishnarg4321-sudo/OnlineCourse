package com.elearning.portal.service;

import com.elearning.portal.dto.request.LoginRequest;
import com.elearning.portal.dto.request.RegisterRequest;
import com.elearning.portal.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
