package com.elearning.portal.dto.response;

import com.elearning.portal.util.enums.AccountStatus;
import com.elearning.portal.util.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private AccountStatus accountStatus;
}
