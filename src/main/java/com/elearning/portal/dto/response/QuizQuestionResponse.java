package com.elearning.portal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizQuestionResponse {
    private Long id;
    private String questionText;
    private List<QuizAnswerResponse> answers;
}
