package com.elearning.portal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModuleRequest {
    @NotBlank
    private String title;
    @NotNull
    private Integer orderIndex;
}
