package com.mj.quicktestpro.service.exam.question;
import com.mj.quicktestpro.dto.exam.AnswerResponseDto;
import com.mj.quicktestpro.dto.general.ReadQuestionResponseAlternativeDto;
import com.mj.quicktestpro.dto.general.ReadQuestionResponseDto;
import com.mj.quicktestpro.dto.question.QuestionRequestDto;
import com.mj.quicktestpro.dto.question.QuestionResponseDto;

import java.util.List;

public interface QuestionServiceInterface {
    ReadQuestionResponseDto createSingleQuestionInstance(QuestionRequestDto questionRequestDto);

    ReadQuestionResponseDto createMultipleQuestion(List<QuestionRequestDto> questionRequestDtoList);

    ReadQuestionResponseAlternativeDto fetchAllQuestions(int pageNo, int pageSize);

    ReadQuestionResponseDto findQuestionById(Long questionId);

    ReadQuestionResponseAlternativeDto findQuestionsByCategory(String category, int pageNo, int pageSize);

    ReadQuestionResponseAlternativeDto findQuestionBySearchQuery(String searchWord, int pageNo, int pageSize);

    ReadQuestionResponseDto updateQuestionInstance(Long questionId, QuestionRequestDto questionRequestDto);

    ReadQuestionResponseDto deleteQuestionById(Long questionId);

    ReadQuestionResponseDto reactivateQuestionById(Long questionId);
    ReadQuestionResponseAlternativeDto generateQuestionsForExamSession(int pageNo, int pageSize, int numberOfQuestions, String category);
    List<QuestionResponseDto> generateQuestionsForExamSession(int numberOfQuestions, String category);

    ReadQuestionResponseAlternativeDto findBatchOfQuestionsByListOfId(List<Long> questionId, int pageNo, int pageSize);

    Integer getScores (List<AnswerResponseDto> responses);
}