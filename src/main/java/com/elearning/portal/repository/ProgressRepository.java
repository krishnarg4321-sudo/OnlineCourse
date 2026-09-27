package com.elearning.portal.repository;

import com.elearning.portal.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByStudentIdAndLessonId(Long studentId, Long lessonId);
    List<Progress> findByStudentIdAndLessonModuleCourseId(Long studentId, Long courseId);
    long countByStudentIdAndLessonModuleCourseIdAndCompletedTrue(Long studentId, Long courseId);
}
