package com.elearning.portal.service;

import com.elearning.portal.dto.request.LoginRequest;
import com.elearning.portal.dto.request.RegisterRequest;
import com.elearning.portal.entity.User;
import com.elearning.portal.exception.ConflictException;
import com.elearning.portal.repository.UserRepository;
import com.elearning.portal.security.JwtUtil;
import com.elearning.portal.service.impl.AuthServiceImpl;
import com.elearning.portal.util.enums.AccountStatus;
import com.elearning.portal.util.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock AuthenticationManager authenticationManager;
    @Mock JwtUtil jwtUtil;

    AuthServiceImpl service;

    @BeforeEach
    void setup() {
        service = new AuthServiceImpl(userRepository, passwordEncoder, authenticationManager, jwtUtil);
    }

    @Test
    void register_duplicateEmail_throwsConflict() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("x@y.com"); req.setPassword("password"); req.setName("x"); req.setRole(Role.STUDENT);
        when(userRepository.existsByEmailIgnoreCase("x@y.com")).thenReturn(true);
        assertThrows(ConflictException.class, () -> service.register(req));
    }

    @Test
    void login_valid_returnsToken() {
        LoginRequest req = new LoginRequest();
        req.setEmail("a@b.com"); req.setPassword("password");
        User user = new User();
        user.setId(1L); user.setEmail("a@b.com"); user.setName("A"); user.setRole(Role.STUDENT); user.setAccountStatus(AccountStatus.ACTIVE);
        when(userRepository.findByEmailIgnoreCase("a@b.com")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any())).thenReturn("jwt");
        var res = service.login(req);
        assertEquals("jwt", res.getToken());
        verify(authenticationManager).authenticate(any());
    }
}
