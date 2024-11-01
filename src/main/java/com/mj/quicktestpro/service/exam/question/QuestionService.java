package com.mj.quicktestpro.service.exam.question;
import com.mj.quicktestpro.dto.exam.AnswerResponseDto;
import com.mj.quicktestpro.dto.exam.CategoryType;
import com.mj.quicktestpro.dto.general.ReadQuestionResponseAlternativeDto;
import com.mj.quicktestpro.dto.general.ReadQuestionResponseDto;
import com.mj.quicktestpro.dto.question.Difficulty;
import com.mj.quicktestpro.dto.question.PageableResponseDto;
import com.mj.quicktestpro.dto.question.QuestionRequestDto;
import com.mj.quicktestpro.dto.question.QuestionResponseDto;
import com.mj.quicktestpro.entity.exam.QuestionInstance;
import com.mj.quicktestpro.exception.BadRequestException;
import com.mj.quicktestpro.exception.QuestionResourceNotFoundException;
import com.mj.quicktestpro.repository.exam.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class QuestionService implements QuestionServiceInterface {

    private QuestionRepository questionRepository;

    @Override
    public ReadQuestionResponseDto createSingleQuestionInstance(QuestionRequestDto questionRequestDto) {
        QuestionInstance questionInstance = QuestionInstance.builder()
                .examCategory(CategoryType.valueOf(questionRequestDto.getExamCategory()))
                .difficultyLevel(Difficulty.valueOf(questionRequestDto.getDifficultyLevel()))
                .title(questionRequestDto.getTitle())
                .option1(questionRequestDto.getOption1())
                .option2(questionRequestDto.getOption2())
                .option3(questionRequestDto.getOption3())
                .option4(questionRequestDto.getOption4())
                .rightAnswer(questionRequestDto.getRightAnswer())
                .isAvailable(true) //available to participant by default
                .build();

        questionRepository.save(questionInstance);

        return mapToReadQuestionResponseDto(String.format("Question with id: '%d' created successfully", questionInstance.getId()), questionInstance);
    }

    @Override
    public ReadQuestionResponseDto createMultipleQuestion(List<QuestionRequestDto> questionRequestDtoList) {
        List<QuestionInstance> questionInstanceList = questionRequestDtoList.stream().map(questionRequestDto -> questionRepository.save(
                QuestionInstance.builder()
                        .examCategory(CategoryType.valueOf(questionRequestDto.getExamCategory()))
                        .difficultyLevel(Difficulty.valueOf(questionRequestDto.getDifficultyLevel()))
                        .title(questionRequestDto.getTitle())
                        .option1(questionRequestDto.getOption1())
                        .option2(questionRequestDto.getOption2())
                        .option3(questionRequestDto.getOption3())
                        .option4(questionRequestDto.getOption4())
                        .rightAnswer(questionRequestDto.getRightAnswer())
                        .isAvailable(true)
                        .build()
        )).toList();

        return ReadQuestionResponseDto.builder()
                .message("Batch of questions successfully created")
                .questionResponseDtoList(questionInstanceList.stream().map(questionInstance -> QuestionResponseDto.builder()
                        .id(questionInstance.getId())
                        .title(questionInstance.getTitle())
                        .option1(questionInstance.getOption1())
                        .option2(questionInstance.getOption2())
                        .option3(questionInstance.getOption3())
                        .option4(questionInstance.getOption4())
                        .build()).collect(Collectors.toList()))
                .build();
    }


    @Override
    public ReadQuestionResponseAlternativeDto fetchAllQuestions(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<QuestionInstance> questionInstances = questionRepository.findAll(pageable);

        if (questionInstances.isEmpty()) {
            throw new QuestionResourceNotFoundException("Question list is empty");
        }

        List<QuestionResponseDto> questionResponseDtoList = mapToQuestionResponseDto(questionInstances);

        String message = "Question list successfully returned according to page specifications";

        return mapToReadQuestionResponseDto(message, questionInstances, questionResponseDtoList);
    }

    @Override
    public ReadQuestionResponseDto findQuestionById(Long questionId) {
        QuestionInstance questionInstance = questionRepository.findById(questionId).orElseThrow(() -> new QuestionResourceNotFoundException(String.format("Question with id: '%d' not found", questionId)));

        return mapToReadQuestionResponseDto("Question requested successfully fetched", questionInstance);
    }

    @Override
    public ReadQuestionResponseAlternativeDto findQuestionsByCategory(String category, int pageNo, int pageSize) {
        List<QuestionInstance> questionInstances = questionRepository.findAll().stream().filter(questionInstance -> questionInstance.getExamCategory().toString().equalsIgnoreCase(category)).toList();

        if (questionInstances.isEmpty()) {
            throw new QuestionResourceNotFoundException("No question in the category requested for");
        }

        Page<QuestionInstance> filteredQuestionInstance = convertListToPage(pageNo, pageSize, questionInstances);

        List<QuestionResponseDto> questionResponseDtoList = mapToQuestionResponseDto(filteredQuestionInstance);

        return mapToReadQuestionResponseDto(String.format("Questions under the requested category: '%s' successfully fetched", category), filteredQuestionInstance, questionResponseDtoList);
    }

    @Override
    public ReadQuestionResponseAlternativeDto findQuestionBySearchQuery(String searchWord, int pageNo, int pageSize) {
        List<QuestionInstance> questionInstances = questionRepository.findAll().stream().filter(questionInstance -> questionInstance.getTitle().toLowerCase().contains(searchWord.toLowerCase())).toList();

        if (questionInstances.isEmpty()) {
            throw new QuestionResourceNotFoundException("The search word used does not match and question title in the repository");
        }

        Page<QuestionInstance> filteredQuestionInstances = convertListToPage(pageNo, pageSize, questionInstances);

        List<QuestionResponseDto> questionResponseDtoList = mapToQuestionResponseDto(filteredQuestionInstances);

        return mapToReadQuestionResponseDto("Successfully retrieved matching list of questions with requested search word", filteredQuestionInstances, questionResponseDtoList);
    }

    @Override
    public ReadQuestionResponseDto updateQuestionInstance(Long questionId, QuestionRequestDto questionRequestDto) {
        QuestionInstance questionInstance = questionRepository.findById(questionId).orElseThrow(() -> new QuestionResourceNotFoundException(String.format("Question with id: '%d' not found", questionId)));

        questionInstance.setExamCategory(CategoryType.valueOf(questionRequestDto.getExamCategory()));
        questionInstance.setTitle(questionRequestDto.getTitle());
        questionInstance.setOption1(questionRequestDto.getOption1());
        questionInstance.setOption2(questionRequestDto.getOption2());
        questionInstance.setOption3(questionRequestDto.getOption3());
        questionInstance.setOption4(questionRequestDto.getOption4());
        questionInstance.setRightAnswer(questionInstance.getRightAnswer());
        questionInstance.setDifficultyLevel(Difficulty.valueOf(questionRequestDto.getDifficultyLevel()));

        questionRepository.save(questionInstance);

        return mapToReadQuestionResponseDto(String.format("Question with id: '%d' successfully updated", questionId), questionInstance);
    }

    @Override
    public ReadQuestionResponseDto deleteQuestionById(Long questionId) {
        QuestionInstance questionInstance = questionRepository.findById(questionId).orElseThrow(() -> new QuestionResourceNotFoundException(String.format("Question with id: '%d' not found", questionId)));

        if (!questionInstance.isAvailable()) {
            throw new BadRequestException(String.format("Question with id: '%d' is already unavailable to participants", questionId));
        }

        questionInstance.setAvailable(false);
        questionRepository.save(questionInstance);

        return mapToReadQuestionResponseDto(String.format("Question with id: '%d' is now unavailable and removed from the questions presented to participants", questionId), questionInstance);
    }

    @Override
    public ReadQuestionResponseDto reactivateQuestionById(Long questionId) {
        QuestionInstance questionInstance = questionRepository.findById(questionId).orElseThrow(() -> new QuestionResourceNotFoundException(String.format("Question with id: '%d' not found", questionId)));

        if (questionInstance.isAvailable()) {
            throw new BadRequestException(String.format("Question with id: '%d' is already available to participants", questionId));
        }
        questionInstance.setAvailable(true);
        questionRepository.save(questionInstance);

        return mapToReadQuestionResponseDto(String.format("Question with id: '%d' is now available and added to the questions presented to participants", questionId), questionInstance);
    }

    @Override
    public ReadQuestionResponseAlternativeDto generateQuestionsForExamSession(int pageNo, int pageSize, int numberOfQuestions, String category) {

        List<QuestionInstance> questionInstances = questionRepository.findAll().stream().filter(questionInstance -> questionInstance.getExamCategory().toString().equalsIgnoreCase(category)).collect(Collectors.toList());

        if (questionInstances.isEmpty()) {
            throw new QuestionResourceNotFoundException("Question with category requested for is empty");
        }

        Collections.shuffle(questionInstances);
        questionInstances = questionInstances.subList(0, numberOfQuestions);

        Page<QuestionInstance> filteredQuestionInstances = convertListToPage(pageNo, pageSize, questionInstances);

        List<QuestionResponseDto> questionResponseDtoList = mapToQuestionResponseDto(filteredQuestionInstances);

        log.info(String.format("the number of questions is '%d'", numberOfQuestions));

        return mapToReadQuestionResponseDto("Questions successfully generated for exam session based on category and number of questions parameter", filteredQuestionInstances, questionResponseDtoList);
    }


    @Override
    public List<QuestionResponseDto> generateQuestionsForExamSession(int numberOfQuestions, String category) {
        List<QuestionInstance> questionInstances = questionRepository.findAll().stream().filter(questionInstance -> questionInstance.getExamCategory().toString().equalsIgnoreCase(category)).collect(Collectors.toList());

        if (questionInstances.isEmpty()) {
            throw new QuestionResourceNotFoundException("Question with category requested for is empty");
        }

        Collections.shuffle(questionInstances);
        questionInstances = questionInstances.subList(0, numberOfQuestions);

        return questionInstances.stream().map(questionInstance -> QuestionResponseDto.builder()
                .id(questionInstance.getId())
                .title(questionInstance.getTitle())
                .option1(questionInstance.getOption1())
                .option2(questionInstance.getOption2())
                .option3(questionInstance.getOption3())
                .option4(questionInstance.getOption4())
                .build()).collect(Collectors.toList());
    }

    @Override
    public ReadQuestionResponseAlternativeDto findBatchOfQuestionsByListOfId(List<Long> questionId, int pageNo, int pageSize) {
        List<QuestionInstance> questionInstances = questionId.stream().map(id -> questionRepository.findById(id).orElseThrow(() -> new QuestionResourceNotFoundException("Question resource not found"))).toList();

        Page<QuestionInstance> questionInstancePage = convertListToPage(pageNo, pageSize, questionInstances);

        List<QuestionResponseDto> questionResponseDtoList = mapToQuestionResponseDto(questionInstancePage);

        return mapToReadQuestionResponseDto("Questions retrieved successfully based on list of Id", questionInstancePage, questionResponseDtoList);
    }

    @Override
    public Integer getScores(List<AnswerResponseDto> responses) {
        Integer rightAnswer = 0;

        for (AnswerResponseDto response : responses){
            QuestionInstance questionInstance = questionRepository.findById(response.getId()).get();
            if(response.getResponse().equalsIgnoreCase(questionInstance.getRightAnswer())){
                rightAnswer++;
            }
        }

        return rightAnswer;
    }


    private Page<QuestionInstance> convertListToPage(int pageNo, int pageSize, List<QuestionInstance> questionInstances) {
        PagedListHolder<QuestionInstance> questionInstancePagedListHolder = new PagedListHolder<>(questionInstances);

        questionInstancePagedListHolder.setPage(pageNo);
        questionInstancePagedListHolder.setPageSize(pageSize);

        return new PageImpl<>(questionInstancePagedListHolder.getPageList(), PageRequest.of(pageNo, pageSize), questionInstances.size());
    }

    private List<QuestionResponseDto> mapToQuestionResponseDto(Page<QuestionInstance> questionInstances) {
        return questionInstances.getContent().stream().map(questionInstance -> QuestionResponseDto.builder()
                .id(questionInstance.getId())
                .title(questionInstance.getTitle())
                .option1(questionInstance.getOption1())
                .option2(questionInstance.getOption2())
                .option3(questionInstance.getOption3())
                .option4(questionInstance.getOption4())
                .build()).collect(Collectors.toList());
    }

    private ReadQuestionResponseAlternativeDto mapToReadQuestionResponseDto(String message, Page<QuestionInstance> questionInstances, List<QuestionResponseDto> questionResponseDtoList) {
        return ReadQuestionResponseAlternativeDto.builder()
                .message(message)
                .pageableResponseDtoList(Collections.singletonList(PageableResponseDto.builder()
                        .questionResponseDtoList(questionResponseDtoList)
                        .pageNo(questionInstances.getNumber())
                        .pageSize(questionInstances.getSize())
                        .totalElements(questionInstances.getTotalElements())
                        .totalPages(questionInstances.getTotalPages())
                        .last(questionInstances.isLast())
                        .build()))
                .build();
    }

    private ReadQuestionResponseDto mapToReadQuestionResponseDto(String message, QuestionInstance questionInstance) {

        return ReadQuestionResponseDto.builder()
                .message(message)
                .questionResponseDtoList(Collections.singletonList(QuestionResponseDto.builder()
                        .id(questionInstance.getId())
                        .title(questionInstance.getTitle())
                        .option1(questionInstance.getOption1())
                        .option2(questionInstance.getOption2())
                        .option3(questionInstance.getOption3())
                        .option4(questionInstance.getOption4())
                        .build()))
                .build();
    }
}
