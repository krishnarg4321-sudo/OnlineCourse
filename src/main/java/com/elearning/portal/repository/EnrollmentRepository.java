package com.elearning.portal.repository;

import com.elearning.portal.entity.Enrollment;
import com.elearning.portal.util.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentIdAndCourseIdAndStatus(Long studentId, Long courseId, EnrollmentStatus status);
    List<Enrollment> findByStudentId(Long studentId);
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
    long countByCourseInstructorId(Long instructorId);
}
