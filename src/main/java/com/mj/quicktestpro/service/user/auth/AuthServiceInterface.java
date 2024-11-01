package com.mj.quicktestpro.service.user.auth;

import com.mj.quicktestpro.dto.general.ResponseDto;
import com.mj.quicktestpro.dto.user.UserLoginDto;
import com.mj.quicktestpro.dto.user.UserRegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthServiceInterface {

    ResponseEntity<ResponseDto> loginUser(UserLoginDto userLoginDto);

    ResponseEntity<ResponseDto> registerUser(UserRegisterDto userRegister);
}
