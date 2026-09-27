package com.elearning.portal.dto.request;

import com.elearning.portal.util.enums.CourseLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String thumbnailUrl;
    @NotNull
    private Long categoryId;
    @NotNull
    private CourseLevel level;
    @NotNull
    private Integer duration;
}
