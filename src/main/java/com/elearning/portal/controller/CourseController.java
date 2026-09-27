package com.elearning.portal.controller;

import com.elearning.portal.dto.request.*;
import com.elearning.portal.dto.response.*;
import com.elearning.portal.service.CourseService;
import com.elearning.portal.util.enums.CourseLevel;
import com.elearning.portal.util.enums.CourseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public Page<CourseSummaryResponse> published(@RequestParam(required = false) String search,
                                                 @RequestParam(required = false) Long category,
                                                 @RequestParam(required = false) CourseLevel level,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return courseService.getPublished(search, category, level, page, size);
    }

    @GetMapping("/courses/{id}")
    public CourseDetailResponse detail(@PathVariable Long id) {
        return courseService.getCourseDetail(id);
    }

    @PostMapping("/courses")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public CourseSummaryResponse create(@Valid @RequestBody CourseRequest request) { return courseService.create(request); }

    @PutMapping("/courses/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public CourseSummaryResponse update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) { return courseService.update(id, request); }

    @DeleteMapping("/courses/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public void delete(@PathVariable Long id) { courseService.delete(id); }

    @PutMapping("/courses/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public CourseSummaryResponse updateStatus(@PathVariable Long id, @RequestParam CourseStatus status) { return courseService.updateStatus(id, status); }

    @GetMapping("/courses/{courseId}/modules")
    public List<ModuleResponse> modules(@PathVariable Long courseId,
                                        @RequestParam(defaultValue = "false") boolean fullContent) {
        return courseService.getModules(courseId, fullContent);
    }

    @PostMapping("/courses/{courseId}/modules")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public ModuleResponse addModule(@PathVariable Long courseId, @Valid @RequestBody ModuleRequest request) { return courseService.addModule(courseId, request); }

    @PutMapping("/modules/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public ModuleResponse updateModule(@PathVariable Long id, @Valid @RequestBody ModuleRequest request) { return courseService.updateModule(id, request); }

    @DeleteMapping("/modules/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public void deleteModule(@PathVariable Long id) { courseService.deleteModule(id); }

    @PostMapping("/modules/{moduleId}/lessons")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public LessonResponse addLesson(@PathVariable Long moduleId, @Valid @RequestBody LessonRequest request) { return courseService.addLesson(moduleId, request); }

    @PutMapping("/lessons/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public LessonResponse updateLesson(@PathVariable Long id, @Valid @RequestBody LessonRequest request) { return courseService.updateLesson(id, request); }

    @DeleteMapping("/lessons/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public void deleteLesson(@PathVariable Long id) { courseService.deleteLesson(id); }

    @PostMapping("/lessons/{lessonId}/materials")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public MaterialResponse addMaterial(@PathVariable Long lessonId, @Valid @RequestBody MaterialRequest request) { return courseService.addMaterial(lessonId, request); }

    @PutMapping("/materials/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public MaterialResponse updateMaterial(@PathVariable Long id, @Valid @RequestBody MaterialRequest request) { return courseService.updateMaterial(id, request); }

    @DeleteMapping("/materials/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public void deleteMaterial(@PathVariable Long id) { courseService.deleteMaterial(id); }
}
