package com.mj.quicktestpro.service.user.role;

import com.mj.quicktestpro.dto.general.ResponseUserTypeDto;

public interface RoleServiceInteface {

    ResponseUserTypeDto assignParticipantRole(Long userId);

    ResponseUserTypeDto assignExaminerRole(Long userId);
}