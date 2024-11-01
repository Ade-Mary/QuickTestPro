package com.mj.quicktestpro.dto.exam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExamUpdateRequestDto {
    private String examinerId;
    private String sessionName;
    private String sessionDescription;
    private Integer numberOfQuestions;
    private String isTimed;
    private Long lengthOfTime;
}
