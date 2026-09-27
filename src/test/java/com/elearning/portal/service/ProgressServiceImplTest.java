package com.elearning.portal.service;

import com.elearning.portal.dto.request.ProgressRequest;
import com.elearning.portal.entity.*;
import com.elearning.portal.repository.*;
import com.elearning.portal.security.UserPrincipal;
import com.elearning.portal.service.EnrollmentService;
import com.elearning.portal.service.impl.ProgressServiceImpl;
import com.elearning.portal.util.enums.AccountStatus;
import com.elearning.portal.util.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressServiceImplTest {
    @Mock ProgressRepository progressRepository;
    @Mock LessonRepository lessonRepository;
    @Mock UserRepository userRepository;
    @Mock EnrollmentRepository enrollmentRepository;
    @Mock EnrollmentService enrollmentService;

    @AfterEach
    void clean() { SecurityContextHolder.clearContext(); }

    @Test
    void markProgress_recalculatesPercentage() {
        var principal = UserPrincipal.builder().id(5L).email("s@x.com").password("x").role(Role.STUDENT).accountStatus(AccountStatus.ACTIVE).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));

        Course course = new Course(); course.setId(10L);
        CourseModule module = new CourseModule(); module.setCourse(course);
        Lesson lesson = new Lesson(); lesson.setId(1L); lesson.setTitle("L1"); lesson.setModule(module);
        User user = new User(); user.setId(5L);
        Enrollment enrollment = new Enrollment(); enrollment.setStudent(user); enrollment.setCourse(course);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(enrollmentService.hasActiveEnrollment(5L, 10L)).thenReturn(true);
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        when(progressRepository.findByStudentIdAndLessonId(5L, 1L)).thenReturn(Optional.empty());
        when(lessonRepository.countByModuleCourseId(10L)).thenReturn(4L);
        when(progressRepository.countByStudentIdAndLessonModuleCourseIdAndCompletedTrue(5L, 10L)).thenReturn(2L);
        when(enrollmentRepository.findByStudentIdAndCourseId(5L, 10L)).thenReturn(Optional.of(enrollment));

        ProgressServiceImpl service = new ProgressServiceImpl(progressRepository, lessonRepository, userRepository, enrollmentRepository, enrollmentService);
        ProgressRequest req = new ProgressRequest(); req.setLessonId(1L); req.setCompleted(true);
        service.markProgress(req);

        verify(enrollmentRepository).save(argThat(e -> e.getProgressPercentage() == 50.0));
        verify(progressRepository).save(any());
    }
}
