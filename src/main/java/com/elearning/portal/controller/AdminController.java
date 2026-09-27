package com.elearning.portal.controller;

import com.elearning.portal.dto.request.CategoryRequest;
import com.elearning.portal.dto.response.CategoryResponse;
import com.elearning.portal.dto.response.CourseSummaryResponse;
import com.elearning.portal.dto.response.EnrollmentResponse;
import com.elearning.portal.dto.response.UserResponse;
import com.elearning.portal.service.*;
import com.elearning.portal.util.enums.CourseStatus;
import com.elearning.portal.util.enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final DashboardService dashboardService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CourseService courseService;

    @GetMapping("/reports")
    public Map<String, Object> reports() {
        return dashboardService.admin();
    }

    @GetMapping("/users")
    public Page<UserResponse> users(@RequestParam(required = false) Role role,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        return userService.list(role, page, size);
    }

    @PostMapping("/categories")
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) { return categoryService.create(request); }

    @PutMapping("/courses/{courseId}/status")
    public CourseSummaryResponse courseStatus(@PathVariable Long courseId, @RequestParam CourseStatus status) {
        return courseService.updateStatus(courseId, status);
    }
}
