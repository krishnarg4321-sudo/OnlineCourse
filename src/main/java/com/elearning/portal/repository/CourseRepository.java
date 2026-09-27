package com.elearning.portal.repository;

import com.elearning.portal.entity.Course;
import com.elearning.portal.util.enums.CourseLevel;
import com.elearning.portal.util.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("""
        select c from Course c
        where c.status = com.elearning.portal.util.enums.CourseStatus.PUBLISHED
          and (:search is null or lower(c.title) like lower(concat('%', :search, '%')) or lower(c.description) like lower(concat('%', :search, '%')))
          and (:categoryId is null or c.category.id = :categoryId)
          and (:level is null or c.level = :level)
        """)
    Page<Course> searchPublished(String search, Long categoryId, CourseLevel level, Pageable pageable);

    List<Course> findByInstructorId(Long instructorId);
    long countByStatus(CourseStatus status);
}
