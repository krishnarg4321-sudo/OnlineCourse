package com.elearning.portal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgressRequest {
    @NotNull
    private Long lessonId;
    @NotNull
    private Boolean completed;
}
