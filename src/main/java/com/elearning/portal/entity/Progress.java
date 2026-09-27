package com.elearning.portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "progress", uniqueConstraints = @UniqueConstraint(name = "uk_progress_student_lesson", columnNames = {"student_id", "lesson_id"}))
public class Progress extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private Boolean completed = Boolean.FALSE;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
