package com.mj.quicktestpro.service.user.userInfo;

import com.mj.quicktestpro.dto.general.ResponseDto;
import com.mj.quicktestpro.dto.general.ResponseUserTypeDto;
import com.mj.quicktestpro.dto.general.UserResponseDto;
import com.mj.quicktestpro.dto.user.UserInfoUpdateDto;

import java.util.List;

public interface UserInfoServiceInterface {

    ResponseDto updateUserInformation(Long userId, UserInfoUpdateDto userInfoUpdateDto);
    List<UserResponseDto> fetchAllUsers();
    UserResponseDto findUserById(Long id);

    List<ResponseUserTypeDto> findUserByUserType(String userType);
    /*
    findAllParticipantsByExamSessionId

     */
}
