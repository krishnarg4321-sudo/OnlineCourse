package com.elearning.portal.repository;

import com.elearning.portal.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByStudentIdOrderBySubmittedAtDesc(Long studentId);
    List<QuizResult> findTop5ByStudentIdOrderBySubmittedAtDesc(Long studentId);
}
