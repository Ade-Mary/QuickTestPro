package com.mj.quicktestpro.dto.general;
import com.mj.quicktestpro.dto.question.QuestionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadQuestionResponseDto {
    private String message;
    private List<QuestionResponseDto> questionResponseDtoList;
}