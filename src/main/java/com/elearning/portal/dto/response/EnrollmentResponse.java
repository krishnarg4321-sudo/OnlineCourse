package com.elearning.portal.dto.response;

import com.elearning.portal.util.enums.EnrollmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EnrollmentResponse {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private EnrollmentStatus status;
    private Double progressPercentage;
    private LocalDateTime enrolledAt;
}
