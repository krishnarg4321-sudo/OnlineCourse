package com.elearning.portal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnswerRequest {
    @NotBlank
    private String answerText;
    private Boolean isCorrect;
}
