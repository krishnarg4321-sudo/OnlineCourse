package com.elearning.portal.service.impl;

import com.elearning.portal.dto.request.EnrollmentRequest;
import com.elearning.portal.dto.response.EnrollmentResponse;
import com.elearning.portal.entity.Enrollment;
import com.elearning.portal.exception.ConflictException;
import com.elearning.portal.exception.ForbiddenException;
import com.elearning.portal.exception.NotFoundException;
import com.elearning.portal.repository.CourseRepository;
import com.elearning.portal.repository.EnrollmentRepository;
import com.elearning.portal.repository.UserRepository;
import com.elearning.portal.service.EnrollmentService;
import com.elearning.portal.util.SecurityUtils;
import com.elearning.portal.util.enums.EnrollmentStatus;
import com.elearning.portal.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        var principal = SecurityUtils.currentUser();
        if (principal == null || principal.getRole() != Role.STUDENT) throw new ForbiddenException("Only students can enroll");
        enrollmentRepository.findByStudentIdAndCourseIdAndStatus(principal.getId(), request.getCourseId(), EnrollmentStatus.ACTIVE)
                .ifPresent(e -> { throw new ConflictException("Already enrolled in this course"); });
        Enrollment e = new Enrollment();
        e.setStudent(userRepository.findById(principal.getId()).orElseThrow(() -> new NotFoundException("Student not found")));
        e.setCourse(courseRepository.findById(request.getCourseId()).orElseThrow(() -> new NotFoundException("Course not found")));
        e.setEnrolledAt(LocalDateTime.now());
        e.setStatus(EnrollmentStatus.ACTIVE);
        e.setProgressPercentage(0d);
        return map(enrollmentRepository.save(e));
    }

    @Override
    public List<EnrollmentResponse> myEnrollments() {
        var principal = SecurityUtils.currentUser();
        if (principal == null || principal.getRole() != Role.STUDENT) throw new ForbiddenException("Only students allowed");
        return enrollmentRepository.findByStudentId(principal.getId()).stream().map(this::map).toList();
    }

    @Override
    public void cancel(Long enrollmentId) {
        var principal = SecurityUtils.currentUser();
        Enrollment e = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new NotFoundException("Enrollment not found"));
        if (principal == null || (!e.getStudent().getId().equals(principal.getId()) && principal.getRole() != Role.ADMIN)) {
            throw new ForbiddenException("Cannot cancel this enrollment");
        }
        e.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(e);
    }

    @Override
    public boolean hasActiveEnrollment(Long studentId, Long courseId) {
        return enrollmentRepository.findByStudentIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.ACTIVE).isPresent();
    }

    private EnrollmentResponse map(Enrollment e) {
        return EnrollmentResponse.builder().id(e.getId()).courseId(e.getCourse().getId()).courseTitle(e.getCourse().getTitle())
                .status(e.getStatus()).progressPercentage(e.getProgressPercentage()).enrolledAt(e.getEnrolledAt()).build();
    }
}
