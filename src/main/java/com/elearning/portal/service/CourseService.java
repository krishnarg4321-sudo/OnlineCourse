package com.elearning.portal.service;

import com.elearning.portal.dto.request.*;
import com.elearning.portal.dto.response.*;
import com.elearning.portal.util.enums.CourseLevel;
import com.elearning.portal.util.enums.CourseStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    Page<CourseSummaryResponse> getPublished(String search, Long categoryId, CourseLevel level, int page, int size);
    CourseDetailResponse getCourseDetail(Long id);
    CourseSummaryResponse create(CourseRequest request);
    CourseSummaryResponse update(Long id, CourseRequest request);
    void delete(Long id);
    CourseSummaryResponse updateStatus(Long id, CourseStatus status);

    List<ModuleResponse> getModules(Long courseId, boolean fullContent);
    ModuleResponse addModule(Long courseId, ModuleRequest request);
    ModuleResponse updateModule(Long moduleId, ModuleRequest request);
    void deleteModule(Long moduleId);

    LessonResponse addLesson(Long moduleId, LessonRequest request);
    LessonResponse updateLesson(Long lessonId, LessonRequest request);
    void deleteLesson(Long lessonId);

    MaterialResponse addMaterial(Long lessonId, MaterialRequest request);
    MaterialResponse updateMaterial(Long materialId, MaterialRequest request);
    void deleteMaterial(Long materialId);
}
