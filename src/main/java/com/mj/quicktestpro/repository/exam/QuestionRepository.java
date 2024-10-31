package com.mj.quicktestpro.repository.exam;
import com.mj.quicktestpro.entity.exam.QuestionInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionInstance, Long> {
}
