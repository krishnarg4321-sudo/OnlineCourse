package com.elearning.portal.dto.request;

import com.elearning.portal.util.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonRequest {
    @NotBlank
    private String title;
    @NotNull
    private Integer orderIndex;
    @NotNull
    private ContentType contentType;
}
