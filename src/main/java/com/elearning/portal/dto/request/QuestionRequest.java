package com.elearning.portal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionRequest {
    @NotBlank
    private String questionText;
}
