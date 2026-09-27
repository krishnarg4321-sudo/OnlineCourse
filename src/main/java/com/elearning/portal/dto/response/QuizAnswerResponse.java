package com.elearning.portal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizAnswerResponse {
    private Long id;
    private String answerText;
}
