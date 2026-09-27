package com.elearning.portal.service;

import com.elearning.portal.dto.request.AnswerRequest;
import com.elearning.portal.dto.request.QuestionRequest;
import com.elearning.portal.dto.request.QuizCreateRequest;
import com.elearning.portal.dto.request.QuizSubmitRequest;
import com.elearning.portal.dto.response.QuizResponse;
import com.elearning.portal.dto.response.QuizResultResponse;

import java.util.List;

public interface QuizService {
    QuizResponse createQuiz(QuizCreateRequest request);
    QuizResponse addQuestion(Long quizId, QuestionRequest request);
    QuizResponse addAnswer(Long questionId, AnswerRequest request);
    QuizResponse getQuizForStudent(Long id);
    QuizResultResponse submitQuiz(Long quizId, QuizSubmitRequest request);
    List<QuizResultResponse> myResults();
}
