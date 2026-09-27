package com.elearning.portal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgressResponse {
    private Long lessonId;
    private String lessonTitle;
    private Boolean completed;
}
