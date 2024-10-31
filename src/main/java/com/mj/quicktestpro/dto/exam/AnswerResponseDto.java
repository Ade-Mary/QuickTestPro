package com.mj.quicktestpro.dto.exam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AnswerResponseDto {

    private Long id;
    private String response;
}
