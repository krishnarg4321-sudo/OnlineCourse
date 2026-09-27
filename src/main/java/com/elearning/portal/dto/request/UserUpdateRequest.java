package com.elearning.portal.dto.request;

import com.elearning.portal.util.enums.AccountStatus;
import com.elearning.portal.util.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    private Role role;
    private AccountStatus accountStatus;
}
