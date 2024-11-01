package com.mj.quicktestpro.dto.exam;
import com.mj.quicktestpro.dto.exam.ActivateSessionDto;
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
    List<AnswerResponseDto> answerResponseDtoList;
}
