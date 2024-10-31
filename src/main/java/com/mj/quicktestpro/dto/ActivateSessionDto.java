package com.mj.quicktestpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivateSessionDto {

    private String examinerId;
    private String sessionId;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class AnswerResponseDto {

        private Long id;
        private String response;
    }
}