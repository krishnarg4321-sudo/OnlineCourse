package com.elearning.portal.controller;

import com.elearning.portal.dto.request.EnrollmentRequest;
import com.elearning.portal.dto.response.EnrollmentResponse;
import com.elearning.portal.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public EnrollmentResponse enroll(@Valid @RequestBody EnrollmentRequest request) {
        return enrollmentService.enroll(request);
    }

    @GetMapping("/my")
    public List<EnrollmentResponse> my() {
        return enrollmentService.myEnrollments();
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        enrollmentService.cancel(id);
    }
}
