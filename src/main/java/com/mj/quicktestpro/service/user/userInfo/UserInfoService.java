package com.mj.quicktestpro.service.user.userInfo;

import com.mj.quicktestpro.dto.general.ResponseDto;
import com.mj.quicktestpro.dto.general.ResponseUserTypeDto;
import com.mj.quicktestpro.dto.general.UserResponseDto;
import com.mj.quicktestpro.dto.user.UserInfoUpdateDto;
import com.mj.quicktestpro.entity.userType.UserType;
import com.mj.quicktestpro.exception.ResourceNotFoundException;
import com.mj.quicktestpro.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoService implements UserInfoServiceInterface {

    private UserRepository userRepository;
    @Override
    public ResponseDto updateUserInformation(Long userId, UserInfoUpdateDto userInfoUpdateDto) {
        UserType user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

//       UserEntity updatedUser = UserEntity.builder()
//               .firstName(userInfoUpdateDto.getFirstname() != null ? userInfoUpdateDto.getFirstname() : user.getFirstName())
//               .lastName(userInfoUpdateDto.getLastname() != null ? userInfoUpdateDto.getLastname() : user.getLastName())
//               .username(userInfoUpdateDto.getUsername() != null ? userInfoUpdateDto.getUsername() : user.getUsername())
//               .email(userInfoUpdateDto.getEmail() != null ? userInfoUpdateDto.getEmail() : user.getEmail())
//               .phoneNumber(userInfoUpdateDto.getPhoneNumber() != null ? userInfoUpdateDto.getPhoneNumber() : user.getPhoneNumber())
//               .dateOfBirth(userInfoUpdateDto.getDateOfBirth() != null ? userInfoUpdateDto.getDateOfBirth() : user.getDateOfBirth())
//               .password(user.getPassword())
//               .build();

        user.setFirstName(userInfoUpdateDto.getFirstname());
        user.setLastName(userInfoUpdateDto.getLastname());
        user.setPhoneNumber(userInfoUpdateDto.getPhoneNumber());
        user.setDateOfBirth(userInfoUpdateDto.getDateOfBirth());


        userRepository.save(user);
        return ResponseDto.builder()
                .message("User details updated successfully")
                .userResponseDto(
                        UserResponseDto.builder()
                                .userId(user.getId())
                                .userName(user.getUsername())
                                .build()
                )
                .build();
    }

    @Override
    public List<UserResponseDto> fetchAllUsers() {
        return userRepository.findAll().stream().map(user -> UserResponseDto.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .build()).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        UserType user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return UserResponseDto.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .build();
    }

    @Override
    public List<ResponseUserTypeDto> findUserByUserType(String userType) {
        List<UserType> userTypeList = userRepository.findAll().stream().filter(user -> user.getRoleEnums().stream().toString().contains(userType)).toList();

        if (userTypeList.isEmpty()){
            throw new ResourceNotFoundException(String.format("User of type: '%s' not found", userType));
        }

        return userTypeList.stream().map(userInstance -> ResponseUserTypeDto.builder()
                .message("Fetched Successfully")
                .userTypeId(userInstance.getParticipantType().getParticipantId())
                .userName(userInstance.getUsername())
                .build()).collect(Collectors.toList());
    }
    }



