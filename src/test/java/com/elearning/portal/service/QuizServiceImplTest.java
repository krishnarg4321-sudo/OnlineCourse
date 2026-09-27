package com.elearning.portal.service;

import com.elearning.portal.dto.request.QuizSubmitRequest;
import com.elearning.portal.entity.*;
import com.elearning.portal.repository.*;
import com.elearning.portal.security.UserPrincipal;
import com.elearning.portal.service.EnrollmentService;
import com.elearning.portal.service.impl.QuizServiceImpl;
import com.elearning.portal.util.enums.AccountStatus;
import com.elearning.portal.util.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {
    @Mock QuizRepository quizRepository;
    @Mock QuestionRepository questionRepository;
    @Mock AnswerRepository answerRepository;
    @Mock LessonRepository lessonRepository;
    @Mock CourseRepository courseRepository;
    @Mock QuizResultRepository quizResultRepository;
    @Mock UserRepository userRepository;
    @Mock EnrollmentService enrollmentService;

    @AfterEach
    void clean() { SecurityContextHolder.clearContext(); }

    @Test
    void submitQuiz_scoresServerSide() {
        var principal = UserPrincipal.builder().id(5L).email("s@x.com").password("x").role(Role.STUDENT).accountStatus(AccountStatus.ACTIVE).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));

        Course course = new Course(); course.setId(1L);
        Quiz quiz = new Quiz(); quiz.setId(1L); quiz.setTitle("Q"); quiz.setCourse(course);
        Question q1 = new Question(); q1.setId(10L); q1.setQuiz(quiz);
        Question q2 = new Question(); q2.setId(20L); q2.setQuiz(quiz);
        Answer a1 = new Answer(); a1.setId(100L); a1.setIsCorrect(true);
        Answer a2 = new Answer(); a2.setId(200L); a2.setIsCorrect(false);
        Answer b1 = new Answer(); b1.setId(300L); b1.setIsCorrect(true);

        User user = new User(); user.setId(5L);

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(enrollmentService.hasActiveEnrollment(5L, 1L)).thenReturn(true);
        when(questionRepository.findByQuizId(1L)).thenReturn(List.of(q1, q2));
        when(answerRepository.findByQuestionId(10L)).thenReturn(List.of(a1, a2));
        when(answerRepository.findByQuestionId(20L)).thenReturn(List.of(b1));
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        when(quizResultRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        QuizServiceImpl service = new QuizServiceImpl(quizRepository, questionRepository, answerRepository, lessonRepository, courseRepository, quizResultRepository, userRepository, enrollmentService);
        QuizSubmitRequest req = new QuizSubmitRequest();
        req.setSelectedAnswers(Map.of(10L, 100L, 20L, 999L));
        var result = service.submitQuiz(1L, req);
        assertEquals(50.0, result.getScore());
    }
}
