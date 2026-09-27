package com.elearning.portal.service;

import com.elearning.portal.dto.request.EnrollmentRequest;
import com.elearning.portal.entity.Enrollment;
import com.elearning.portal.exception.ConflictException;
import com.elearning.portal.repository.CourseRepository;
import com.elearning.portal.repository.EnrollmentRepository;
import com.elearning.portal.repository.UserRepository;
import com.elearning.portal.security.UserPrincipal;
import com.elearning.portal.service.impl.EnrollmentServiceImpl;
import com.elearning.portal.util.enums.AccountStatus;
import com.elearning.portal.util.enums.EnrollmentStatus;
import com.elearning.portal.util.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {
    @Mock EnrollmentRepository enrollmentRepository;
    @Mock CourseRepository courseRepository;
    @Mock UserRepository userRepository;

    @AfterEach
    void clean() { SecurityContextHolder.clearContext(); }

    @Test
    void duplicateEnrollment_rejected() {
        var principal = UserPrincipal.builder().id(5L).email("s@x.com").password("x").role(Role.STUDENT).accountStatus(AccountStatus.ACTIVE).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));

        EnrollmentRequest req = new EnrollmentRequest();
        req.setCourseId(2L);
        when(enrollmentRepository.findByStudentIdAndCourseIdAndStatus(5L, 2L, EnrollmentStatus.ACTIVE)).thenReturn(Optional.of(new Enrollment()));

        EnrollmentServiceImpl service = new EnrollmentServiceImpl(enrollmentRepository, courseRepository, userRepository);
        assertThrows(ConflictException.class, () -> service.enroll(req));
    }
}
