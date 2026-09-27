package com.elearning.portal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuizCreateRequest {
    private Long lessonId;
    private Long courseId;
    @NotBlank
    private String title;
}
