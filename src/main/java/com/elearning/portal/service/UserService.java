package com.elearning.portal.service;

import com.elearning.portal.dto.request.UserUpdateRequest;
import com.elearning.portal.dto.response.UserResponse;
import com.elearning.portal.util.enums.Role;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponse getById(Long id);
    UserResponse update(Long id, UserUpdateRequest request);
    Page<UserResponse> list(Role role, int page, int size);
}
