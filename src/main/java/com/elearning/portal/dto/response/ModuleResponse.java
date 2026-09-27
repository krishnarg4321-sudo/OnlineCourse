package com.elearning.portal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ModuleResponse {
    private Long id;
    private String title;
    private Integer orderIndex;
    private List<LessonResponse> lessons;
}
