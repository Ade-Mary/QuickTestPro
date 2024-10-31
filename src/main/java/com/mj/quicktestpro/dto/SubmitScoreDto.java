package com.mj.quicktestpro.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitScoreDto {

    private String sessionId;
    private String participantId;
    List<ActivateSessionDto.AnswerResponseDto> answerResponseDtoList;
}
