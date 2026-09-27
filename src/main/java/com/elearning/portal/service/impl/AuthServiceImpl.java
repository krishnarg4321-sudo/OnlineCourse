package com.elearning.portal.service.impl;

import com.elearning.portal.dto.request.LoginRequest;
import com.elearning.portal.dto.request.RegisterRequest;
import com.elearning.portal.dto.response.AuthResponse;
import com.elearning.portal.entity.User;
import com.elearning.portal.exception.ConflictException;
import com.elearning.portal.exception.UnauthorizedException;
import com.elearning.portal.repository.UserRepository;
import com.elearning.portal.security.JwtUtil;
import com.elearning.portal.security.UserPrincipal;
import com.elearning.portal.service.AuthService;
import com.elearning.portal.util.enums.AccountStatus;
import com.elearning.portal.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == Role.ADMIN) {
            throw new UnauthorizedException("Admin registration is not allowed");
        }
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail().toLowerCase());
        user.setRole(request.getRole());
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        UserPrincipal principal = toPrincipal(user);
        return AuthResponse.builder()
                .token(jwtUtil.generateToken(principal))
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (user.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new UnauthorizedException("Account is disabled");
        }
        UserPrincipal principal = toPrincipal(user);
        return AuthResponse.builder()
                .token(jwtUtil.generateToken(principal))
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    private UserPrincipal toPrincipal(User user) {
        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPasswordHash())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .build();
    }
}
