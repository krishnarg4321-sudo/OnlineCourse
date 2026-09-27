package com.elearning.portal.repository;

import com.elearning.portal.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByLessonId(Long lessonId);
}
