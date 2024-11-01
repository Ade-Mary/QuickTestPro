package com.mj.quicktestpro.dto.general;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRoleResponseDto {
    private String userId;
    private String userName;
}
