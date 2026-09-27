package com.elearning.portal.service.impl;

import com.elearning.portal.dto.request.*;
import com.elearning.portal.dto.response.*;
import com.elearning.portal.entity.*;
import com.elearning.portal.exception.ForbiddenException;
import com.elearning.portal.exception.NotFoundException;
import com.elearning.portal.repository.*;
import com.elearning.portal.service.CourseService;
import com.elearning.portal.service.EnrollmentService;
import com.elearning.portal.util.SecurityUtils;
import com.elearning.portal.util.enums.CourseLevel;
import com.elearning.portal.util.enums.CourseStatus;
import com.elearning.portal.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CourseModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final MaterialRepository materialRepository;
    private final EnrollmentService enrollmentService;

    @Override
    public Page<CourseSummaryResponse> getPublished(String search, Long categoryId, CourseLevel level, int page, int size) {
        Long uid = SecurityUtils.currentUser() == null ? null : SecurityUtils.currentUser().getId();
        return courseRepository.searchPublished(search, categoryId, level, PageRequest.of(page, size)).map(c -> mapSummary(c, uid));
    }

    @Override
    public CourseDetailResponse getCourseDetail(Long id) {
        Course c = findCourse(id);
        boolean full = canViewFull(c);
        return CourseDetailResponse.builder()
                .id(c.getId()).title(c.getTitle()).description(c.getDescription()).thumbnailUrl(c.getThumbnailUrl())
                .level(c.getLevel()).duration(c.getDuration()).status(c.getStatus())
                .categoryName(c.getCategory().getName()).instructorName(c.getInstructor().getName())
                .modules(getModules(id, full)).build();
    }

    @Override
    public CourseSummaryResponse create(CourseRequest request) {
        var principal = requireInstructorOrAdmin();
        Course c = new Course();
        c.setTitle(request.getTitle());
        c.setDescription(request.getDescription());
        c.setThumbnailUrl(request.getThumbnailUrl());
        c.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found")));
        c.setInstructor(userRepository.findById(principal.getId()).orElseThrow(() -> new NotFoundException("User not found")));
        c.setLevel(request.getLevel());
        c.setDuration(request.getDuration());
        c.setStatus(CourseStatus.DRAFT);
        return mapSummary(courseRepository.save(c), principal.getId());
    }

    @Override
    public CourseSummaryResponse update(Long id, CourseRequest request) {
        Course c = findCourse(id);
        validateOwner(c);
        c.setTitle(request.getTitle());
        c.setDescription(request.getDescription());
        c.setThumbnailUrl(request.getThumbnailUrl());
        c.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found")));
        c.setLevel(request.getLevel());
        c.setDuration(request.getDuration());
        if (c.getStatus() == CourseStatus.PUBLISHED) c.setStatus(CourseStatus.PENDING_APPROVAL);
        return mapSummary(courseRepository.save(c), SecurityUtils.currentUser().getId());
    }

    @Override
    public void delete(Long id) {
        Course c = findCourse(id);
        var principal = SecurityUtils.currentUser();
        if (principal.getRole() == Role.ADMIN || (principal.getRole() == Role.INSTRUCTOR && c.getInstructor().getId().equals(principal.getId()) && c.getStatus() != CourseStatus.PUBLISHED)) {
            courseRepository.delete(c);
            return;
        }
        throw new ForbiddenException("Cannot delete course");
    }

    @Override
    public CourseSummaryResponse updateStatus(Long id, CourseStatus status) {
        Course c = findCourse(id);
        c.setStatus(status);
        return mapSummary(courseRepository.save(c), SecurityUtils.currentUser() == null ? null : SecurityUtils.currentUser().getId());
    }

    @Override
    public List<ModuleResponse> getModules(Long courseId, boolean fullContent) {
        Course c = findCourse(courseId);
        if (!fullContent && c.getStatus() != CourseStatus.PUBLISHED) throw new ForbiddenException("Course not publicly accessible");
        return moduleRepository.findByCourseIdOrderByOrderIndexAsc(courseId).stream().map(m -> mapModule(m, fullContent)).toList();
    }

    @Override
    public ModuleResponse addModule(Long courseId, ModuleRequest request) {
        Course c = findCourse(courseId);
        validateOwner(c);
        CourseModule module = new CourseModule();
        module.setCourse(c);
        module.setTitle(request.getTitle());
        module.setOrderIndex(request.getOrderIndex());
        return mapModule(moduleRepository.save(module), true);
    }

    @Override
    public ModuleResponse updateModule(Long moduleId, ModuleRequest request) {
        CourseModule module = moduleRepository.findById(moduleId).orElseThrow(() -> new NotFoundException("Module not found"));
        validateOwner(module.getCourse());
        module.setTitle(request.getTitle());
        module.setOrderIndex(request.getOrderIndex());
        return mapModule(moduleRepository.save(module), true);
    }

    @Override
    public void deleteModule(Long moduleId) {
        CourseModule module = moduleRepository.findById(moduleId).orElseThrow(() -> new NotFoundException("Module not found"));
        validateOwner(module.getCourse());
        moduleRepository.delete(module);
    }

    @Override
    public LessonResponse addLesson(Long moduleId, LessonRequest request) {
        CourseModule module = moduleRepository.findById(moduleId).orElseThrow(() -> new NotFoundException("Module not found"));
        validateOwner(module.getCourse());
        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle(request.getTitle());
        lesson.setOrderIndex(request.getOrderIndex());
        lesson.setContentType(request.getContentType());
        return mapLesson(lessonRepository.save(lesson), true);
    }

    @Override
    public LessonResponse updateLesson(Long lessonId, LessonRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));
        validateOwner(lesson.getModule().getCourse());
        lesson.setTitle(request.getTitle());
        lesson.setOrderIndex(request.getOrderIndex());
        lesson.setContentType(request.getContentType());
        return mapLesson(lessonRepository.save(lesson), true);
    }

    @Override
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));
        validateOwner(lesson.getModule().getCourse());
        lessonRepository.delete(lesson);
    }

    @Override
    public MaterialResponse addMaterial(Long lessonId, MaterialRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));
        validateOwner(lesson.getModule().getCourse());
        Material m = new Material();
        m.setLesson(lesson);
        m.setType(request.getType());
        m.setUrlOrReference(request.getUrlOrReference());
        return mapMaterial(materialRepository.save(m));
    }

    @Override
    public MaterialResponse updateMaterial(Long materialId, MaterialRequest request) {
        Material m = materialRepository.findById(materialId).orElseThrow(() -> new NotFoundException("Material not found"));
        validateOwner(m.getLesson().getModule().getCourse());
        m.setType(request.getType());
        m.setUrlOrReference(request.getUrlOrReference());
        return mapMaterial(materialRepository.save(m));
    }

    @Override
    public void deleteMaterial(Long materialId) {
        Material m = materialRepository.findById(materialId).orElseThrow(() -> new NotFoundException("Material not found"));
        validateOwner(m.getLesson().getModule().getCourse());
        materialRepository.delete(m);
    }

    private Course findCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course not found"));
    }

    private boolean canViewFull(Course c) {
        var principal = SecurityUtils.currentUser();
        if (principal == null) return false;
        if (principal.getRole() == Role.ADMIN) return true;
        if (principal.getRole() == Role.INSTRUCTOR && c.getInstructor().getId().equals(principal.getId())) return true;
        return enrollmentService.hasActiveEnrollment(principal.getId(), c.getId());
    }

    private void validateOwner(Course c) {
        var principal = requireInstructorOrAdmin();
        if (principal.getRole() == Role.ADMIN) return;
        if (!c.getInstructor().getId().equals(principal.getId())) throw new ForbiddenException("You can only manage your own course");
    }

    private com.elearning.portal.security.UserPrincipal requireInstructorOrAdmin() {
        var principal = SecurityUtils.currentUser();
        if (principal == null || (principal.getRole() != Role.ADMIN && principal.getRole() != Role.INSTRUCTOR)) {
            throw new ForbiddenException("Only instructor/admin allowed");
        }
        return principal;
    }

    private CourseSummaryResponse mapSummary(Course c, Long userId) {
        boolean enrolled = userId != null && enrollmentService.hasActiveEnrollment(userId, c.getId());
        return CourseSummaryResponse.builder()
                .id(c.getId()).title(c.getTitle()).description(c.getDescription()).thumbnailUrl(c.getThumbnailUrl())
                .level(c.getLevel()).duration(c.getDuration()).status(c.getStatus())
                .categoryName(c.getCategory().getName()).instructorName(c.getInstructor().getName()).enrolled(enrolled).build();
    }

    private ModuleResponse mapModule(CourseModule module, boolean fullContent) {
        return ModuleResponse.builder().id(module.getId()).title(module.getTitle()).orderIndex(module.getOrderIndex())
                .lessons(lessonRepository.findByModuleIdOrderByOrderIndexAsc(module.getId()).stream().map(l -> mapLesson(l, fullContent)).toList())
                .build();
    }

    private LessonResponse mapLesson(Lesson lesson, boolean fullContent) {
        return LessonResponse.builder()
                .id(lesson.getId()).title(lesson.getTitle()).orderIndex(lesson.getOrderIndex()).contentType(lesson.getContentType())
                .locked(!fullContent)
                .materials(fullContent ? materialRepository.findByLessonId(lesson.getId()).stream().map(this::mapMaterial).toList() : List.of())
                .build();
    }

    private MaterialResponse mapMaterial(Material m) {
        return MaterialResponse.builder().id(m.getId()).type(m.getType()).urlOrReference(m.getUrlOrReference()).build();
    }
}
