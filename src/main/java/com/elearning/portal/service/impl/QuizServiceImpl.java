package com.elearning.portal.service.impl;

import com.elearning.portal.dto.request.AnswerRequest;
import com.elearning.portal.dto.request.QuestionRequest;
import com.elearning.portal.dto.request.QuizCreateRequest;
import com.elearning.portal.dto.request.QuizSubmitRequest;
import com.elearning.portal.dto.response.*;
import com.elearning.portal.entity.*;
import com.elearning.portal.exception.ForbiddenException;
import com.elearning.portal.exception.NotFoundException;
import com.elearning.portal.repository.*;
import com.elearning.portal.service.EnrollmentService;
import com.elearning.portal.service.QuizService;
import com.elearning.portal.util.SecurityUtils;
import com.elearning.portal.util.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final QuizResultRepository quizResultRepository;
    private final UserRepository userRepository;
    private final EnrollmentService enrollmentService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public QuizResponse createQuiz(QuizCreateRequest request) {
        requireInstructorOrAdmin();
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        if (request.getLessonId() != null) {
            quiz.setLesson(lessonRepository.findById(request.getLessonId()).orElseThrow(() -> new NotFoundException("Lesson not found")));
        }
        if (request.getCourseId() != null) {
            quiz.setCourse(courseRepository.findById(request.getCourseId()).orElseThrow(() -> new NotFoundException("Course not found")));
        }
        return mapQuiz(quizRepository.save(quiz), false);
    }

    @Override
    public QuizResponse addQuestion(Long quizId, QuestionRequest request) {
        requireInstructorOrAdmin();
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new NotFoundException("Quiz not found"));
        Question q = new Question();
        q.setQuiz(quiz);
        q.setQuestionText(request.getQuestionText());
        questionRepository.save(q);
        return mapQuiz(quiz, false);
    }

    @Override
    public QuizResponse addAnswer(Long questionId, AnswerRequest request) {
        requireInstructorOrAdmin();
        Question q = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Question not found"));
        Answer a = new Answer();
        a.setQuestion(q);
        a.setAnswerText(request.getAnswerText());
        a.setIsCorrect(Boolean.TRUE.equals(request.getIsCorrect()));
        answerRepository.save(a);
        return mapQuiz(q.getQuiz(), false);
    }

    @Override
    public QuizResponse getQuizForStudent(Long id) {
        return mapQuiz(quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found")), true);
    }

    @Override
    public QuizResultResponse submitQuiz(Long quizId, QuizSubmitRequest request) {
        var principal = SecurityUtils.currentUser();
        if (principal == null || principal.getRole() != Role.STUDENT) throw new ForbiddenException("Only students allowed");
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new NotFoundException("Quiz not found"));
        Long courseId = quiz.getCourse() != null ? quiz.getCourse().getId() : quiz.getLesson().getModule().getCourse().getId();
        if (!enrollmentService.hasActiveEnrollment(principal.getId(), courseId)) throw new ForbiddenException("Not enrolled");

        List<Question> questions = questionRepository.findByQuizId(quizId);
        int correct = 0;
        for (Question question : questions) {
            Long selected = request.getSelectedAnswers().get(question.getId());
            if (selected == null) continue;
            List<Answer> answers = answerRepository.findByQuestionId(question.getId());
            boolean ok = answers.stream().anyMatch(a -> a.getId().equals(selected) && Boolean.TRUE.equals(a.getIsCorrect()));
            if (ok) correct++;
        }
        double score = questions.isEmpty() ? 0d : (correct * 100.0 / questions.size());
        QuizResult result = new QuizResult();
        result.setQuiz(quiz);
        result.setStudent(userRepository.findById(principal.getId()).orElseThrow(() -> new NotFoundException("Student not found")));
        result.setScore(score);
        result.setSubmittedAt(LocalDateTime.now());
        try {
            result.setAnswersJson(objectMapper.writeValueAsString(request.getSelectedAnswers()));
        } catch (Exception e) {
            result.setAnswersJson("{}");
        }
        result = quizResultRepository.save(result);
        return QuizResultResponse.builder().id(result.getId()).quizId(quiz.getId()).quizTitle(quiz.getTitle()).score(score).submittedAt(result.getSubmittedAt()).build();
    }

    @Override
    public List<QuizResultResponse> myResults() {
        var principal = SecurityUtils.currentUser();
        if (principal == null || principal.getRole() != Role.STUDENT) throw new ForbiddenException("Only students allowed");
        return quizResultRepository.findByStudentIdOrderBySubmittedAtDesc(principal.getId()).stream().map(r ->
                QuizResultResponse.builder().id(r.getId()).quizId(r.getQuiz().getId()).quizTitle(r.getQuiz().getTitle()).score(r.getScore()).submittedAt(r.getSubmittedAt()).build()).toList();
    }

    private void requireInstructorOrAdmin() {
        var principal = SecurityUtils.currentUser();
        if (principal == null || (principal.getRole() != Role.ADMIN && principal.getRole() != Role.INSTRUCTOR)) {
            throw new ForbiddenException("Only instructor/admin allowed");
        }
    }

    private QuizResponse mapQuiz(Quiz quiz, boolean studentView) {
        List<QuizQuestionResponse> questionResponses = questionRepository.findByQuizId(quiz.getId()).stream().map(q ->
                QuizQuestionResponse.builder().id(q.getId()).questionText(q.getQuestionText())
                        .answers(answerRepository.findByQuestionId(q.getId()).stream().map(a ->
                                QuizAnswerResponse.builder().id(a.getId()).answerText(a.getAnswerText()).build()).toList())
                        .build()
        ).toList();
        return QuizResponse.builder().id(quiz.getId()).title(quiz.getTitle()).questions(questionResponses).build();
    }
}
