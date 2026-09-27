package com.elearning.portal.service;

import com.elearning.portal.dto.request.EnrollmentRequest;
import com.elearning.portal.dto.response.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse enroll(EnrollmentRequest request);
    List<EnrollmentResponse> myEnrollments();
    void cancel(Long enrollmentId);
    boolean hasActiveEnrollment(Long studentId, Long courseId);
}
