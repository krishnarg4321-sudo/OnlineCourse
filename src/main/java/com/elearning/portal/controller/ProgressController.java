package com.elearning.portal.controller;

import com.elearning.portal.dto.request.ProgressRequest;
import com.elearning.portal.dto.response.ProgressResponse;
import com.elearning.portal.service.ProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {
    private final ProgressService progressService;

    @PostMapping
    public ProgressResponse mark(@Valid @RequestBody ProgressRequest request) {
        return progressService.markProgress(request);
    }

    @GetMapping("/course/{courseId}")
    public List<ProgressResponse> byCourse(@PathVariable Long courseId) {
        return progressService.courseProgress(courseId);
    }
}
