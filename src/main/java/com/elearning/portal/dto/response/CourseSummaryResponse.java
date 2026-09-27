package com.elearning.portal.dto.response;

import com.elearning.portal.util.enums.CourseLevel;
import com.elearning.portal.util.enums.CourseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseSummaryResponse {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private CourseLevel level;
    private Integer duration;
    private CourseStatus status;
    private String categoryName;
    private String instructorName;
    private Boolean enrolled;
}
