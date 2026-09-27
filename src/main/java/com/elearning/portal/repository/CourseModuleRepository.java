package com.elearning.portal.repository;

import com.elearning.portal.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
    List<CourseModule> findByCourseIdOrderByOrderIndexAsc(Long courseId);
}
