package com.elearning.portal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialRequest {
    @NotBlank
    private String type;
    @NotBlank
    private String urlOrReference;
}
