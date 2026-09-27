package com.elearning.portal.repository;

import com.elearning.portal.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByModuleIdOrderByOrderIndexAsc(Long moduleId);
    long countByModuleCourseId(Long courseId);
}
