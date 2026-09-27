package com.elearning.portal.service.impl;

import com.elearning.portal.dto.request.ProgressRequest;
import com.elearning.portal.dto.response.ProgressResponse;
import com.elearning.portal.entity.Progress;
import com.elearning.portal.exception.ForbiddenException;
import com.elearning.portal.exception.NotFoundException;
import com.elearning.portal.repository.*;
import com.elearning.portal.service.EnrollmentService;
import com.elearning.portal.service.ProgressService;
import com.elearning.portal.util.SecurityUtils;
import com.elearning.portal.util.enums.EnrollmentStatus;
import com.elearning.portal.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {
    private final ProgressRepository progressRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentService enrollmentService;

    @Override
    public ProgressResponse markProgress(ProgressRequest request) {
        var principal = SecurityUtils.currentUser();
        if (principal == null || principal.getRole() != Role.STUDENT) throw new ForbiddenException("Only students allowed");
        var lesson = lessonRepository.findById(request.getLessonId()).orElseThrow(() -> new NotFoundException("Lesson not found"));
        Long courseId = lesson.getModule().getCourse().getId();
        if (!enrollmentService.hasActiveEnrollment(principal.getId(), courseId)) throw new ForbiddenException("Not enrolled");
        Progress p = progressRepository.findByStudentIdAndLessonId(principal.getId(), request.getLessonId()).orElseGet(Progress::new);
        p.setStudent(userRepository.findById(principal.getId()).orElseThrow(() -> new NotFoundException("Student not found")));
        p.setLesson(lesson);
        p.setCompleted(request.getCompleted());
        p.setCompletedAt(Boolean.TRUE.equals(request.getCompleted()) ? LocalDateTime.now() : null);
        progressRepository.save(p);
        recalcEnrollment(principal.getId(), courseId);
        return ProgressResponse.builder().lessonId(lesson.getId()).lessonTitle(lesson.getTitle()).completed(p.getCompleted()).build();
    }

    @Override
    public List<ProgressResponse> courseProgress(Long courseId) {
        var principal = SecurityUtils.currentUser();
        if (principal == null || principal.getRole() != Role.STUDENT) throw new ForbiddenException("Only students allowed");
        if (!enrollmentService.hasActiveEnrollment(principal.getId(), courseId)) throw new ForbiddenException("Not enrolled");
        return progressRepository.findByStudentIdAndLessonModuleCourseId(principal.getId(), courseId)
                .stream().map(p -> ProgressResponse.builder().lessonId(p.getLesson().getId()).lessonTitle(p.getLesson().getTitle()).completed(p.getCompleted()).build())
                .toList();
    }

    private void recalcEnrollment(Long studentId, Long courseId) {
        long total = lessonRepository.countByModuleCourseId(courseId);
        long completed = progressRepository.countByStudentIdAndLessonModuleCourseIdAndCompletedTrue(studentId, courseId);
        double pct = total == 0 ? 0d : (completed * 100.0) / total;
        var enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new NotFoundException("Enrollment not found"));
        enrollment.setProgressPercentage(pct);
        if (pct >= 100d) enrollment.setStatus(EnrollmentStatus.COMPLETED);
        enrollmentRepository.save(enrollment);
    }
}
