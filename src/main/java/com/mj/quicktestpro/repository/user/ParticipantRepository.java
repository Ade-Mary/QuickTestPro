package com.mj.quicktestpro.repository.user;

import com.mj.quicktestpro.entity.userType.ParticipantType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<ParticipantType, String> {
}
