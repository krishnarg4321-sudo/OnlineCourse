package com.elearning.portal.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

@Data
public class QuizSubmitRequest {
    @NotEmpty
    private Map<Long, Long> selectedAnswers;
}
