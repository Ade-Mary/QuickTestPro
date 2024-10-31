package com.mj.quicktestpro.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExamRequestDto {

    private String category;
    private String examinerId;
    private String sessionName;
    private String sessionDescription;
    private Integer numberOfQuestions;
    private String isTimed;
    private Long lengthOfTime;
//    private List<QuestionViewDto> listOfQuestions;

}