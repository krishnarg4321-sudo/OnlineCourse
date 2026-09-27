package com.elearning.portal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizResponse {
    private Long id;
    private String title;
    private List<QuizQuestionResponse> questions;
}
