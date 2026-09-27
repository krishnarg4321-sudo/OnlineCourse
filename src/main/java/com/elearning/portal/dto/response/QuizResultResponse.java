package com.elearning.portal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuizResultResponse {
    private Long id;
    private Long quizId;
    private String quizTitle;
    private Double score;
    private LocalDateTime submittedAt;
}
