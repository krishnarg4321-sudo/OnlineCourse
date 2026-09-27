package com.elearning.portal.controller;

import com.elearning.portal.dto.request.AnswerRequest;
import com.elearning.portal.dto.request.QuestionRequest;
import com.elearning.portal.dto.request.QuizCreateRequest;
import com.elearning.portal.dto.request.QuizSubmitRequest;
import com.elearning.portal.dto.response.QuizResponse;
import com.elearning.portal.dto.response.QuizResultResponse;
import com.elearning.portal.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public QuizResponse create(@Valid @RequestBody QuizCreateRequest request) { return quizService.createQuiz(request); }

    @PostMapping("/{quizId}/questions")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public QuizResponse addQuestion(@PathVariable Long quizId, @Valid @RequestBody QuestionRequest request) { return quizService.addQuestion(quizId, request); }

    @PostMapping("/questions/{questionId}/answers")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    public QuizResponse addAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerRequest request) { return quizService.addAnswer(questionId, request); }

    @GetMapping("/{id}")
    public QuizResponse get(@PathVariable Long id) { return quizService.getQuizForStudent(id); }

    @PostMapping("/{id}/submit")
    public QuizResultResponse submit(@PathVariable Long id, @Valid @RequestBody QuizSubmitRequest request) { return quizService.submitQuiz(id, request); }

    @GetMapping("/results/my")
    public List<QuizResultResponse> my() { return quizService.myResults(); }
}
