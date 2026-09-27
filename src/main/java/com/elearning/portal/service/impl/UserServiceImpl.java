package com.elearning.portal.service.impl;

import com.elearning.portal.dto.request.UserUpdateRequest;
import com.elearning.portal.dto.response.UserResponse;
import com.elearning.portal.entity.User;
import com.elearning.portal.exception.ConflictException;
import com.elearning.portal.exception.ForbiddenException;
import com.elearning.portal.exception.NotFoundException;
import com.elearning.portal.repository.UserRepository;
import com.elearning.portal.service.UserService;
import com.elearning.portal.util.SecurityUtils;
import com.elearning.portal.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse getById(Long id) {
        var principal = SecurityUtils.currentUser();
        if (principal == null || (!principal.getId().equals(id) && principal.getRole() != Role.ADMIN)) {
            throw new ForbiddenException("Access denied");
        }
        return map(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        var principal = SecurityUtils.currentUser();
        if (principal == null || (!principal.getId().equals(id) && principal.getRole() != Role.ADMIN)) {
            throw new ForbiddenException("Access denied");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail().toLowerCase());
        if (principal.getRole() == Role.ADMIN && request.getRole() != null) user.setRole(request.getRole());
        if (principal.getRole() == Role.ADMIN && request.getAccountStatus() != null) user.setAccountStatus(request.getAccountStatus());
        return map(userRepository.save(user));
    }

    @Override
    public Page<UserResponse> list(Role role, int page, int size) {
        Page<User> users = role == null ? userRepository.findAll(PageRequest.of(page, size)) : userRepository.findByRole(role, PageRequest.of(page, size));
        return users.map(this::map);
    }

    private UserResponse map(User user) {
        return UserResponse.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).role(user.getRole()).accountStatus(user.getAccountStatus()).build();
    }
}
