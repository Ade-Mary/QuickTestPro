package com.mj.quicktestpro.repository.user;

import com.mj.quicktestpro.entity.userType.ExaminerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminerRepository extends JpaRepository<ExaminerType, String> {
}
