package com.elearning.portal.dto.response;

import com.elearning.portal.util.enums.ContentType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LessonResponse {
    private Long id;
    private String title;
    private Integer orderIndex;
    private ContentType contentType;
    private Boolean locked;
    private List<MaterialResponse> materials;
}
