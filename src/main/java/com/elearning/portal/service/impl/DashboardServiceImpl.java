package com.elearning.portal.service.impl;

import com.elearning.portal.repository.CourseRepository;
import com.elearning.portal.repository.EnrollmentRepository;
import com.elearning.portal.repository.QuizResultRepository;
import com.elearning.portal.repository.UserRepository;
import com.elearning.portal.service.DashboardService;
import com.elearning.portal.util.SecurityUtils;
import com.elearning.portal.util.enums.CourseStatus;
import com.elearning.portal.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final EnrollmentRepository enrollmentRepository;
    private final QuizResultRepository quizResultRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> student() {
        var principal = SecurityUtils.currentUser();
        var enrollments = enrollmentRepository.findByStudentId(principal.getId());
        double avg = enrollments.stream().mapToDouble(e -> e.getProgressPercentage() == null ? 0d : e.getProgressPercentage()).average().orElse(0d);
        long completed = enrollments.stream().filter(e -> e.getProgressPercentage() != null && e.getProgressPercentage() >= 100d).count();
        return Map.of(
                "enrolledCourses", enrollments.size(),
                "avgProgress", avg,
                "completedCourses", completed,
                "recentQuizResults", quizResultRepository.findTop5ByStudentIdOrderBySubmittedAtDesc(principal.getId())
        );
    }

    @Override
    public Map<String, Object> instructor() {
        var principal = SecurityUtils.currentUser();
        var courses = courseRepository.findByInstructorId(principal.getId());
        return Map.of(
                "ownedCourses", courses.size(),
                "totalEnrollments", enrollmentRepository.countByCourseInstructorId(principal.getId()),
                "courses", courses
        );
    }

    @Override
    public Map<String, Object> admin() {
        return Map.of(
                "students", userRepository.findByRole(Role.STUDENT, org.springframework.data.domain.Pageable.unpaged()).getTotalElements(),
                "instructors", userRepository.findByRole(Role.INSTRUCTOR, org.springframework.data.domain.Pageable.unpaged()).getTotalElements(),
                "admins", userRepository.findByRole(Role.ADMIN, org.springframework.data.domain.Pageable.unpaged()).getTotalElements(),
                "publishedCourses", courseRepository.countByStatus(CourseStatus.PUBLISHED),
                "pendingCourses", courseRepository.countByStatus(CourseStatus.PENDING_APPROVAL),
                "totalEnrollments", enrollmentRepository.count()
        );
    }
}
