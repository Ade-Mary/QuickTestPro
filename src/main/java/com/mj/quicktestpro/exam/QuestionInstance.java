package com.mj.quicktestpro.exam;

import com.mj.quicktestpro.dto.CategoryType;
import com.mj.quicktestpro.dto.question.Difficulty;
import jakarta.persistence.*;

public class QuestionInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CategoryType examCategory; //category
    @Enumerated(EnumType.STRING)
    private Difficulty difficultyLevel;
    private String title;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private boolean isAvailable;

    /**
     * Define the Skill Levels:
     *
     * Junior: Basic knowledge and skills, suitable for entry-level positions or beginners.
     * Mid: Intermediate skills and knowledge, suitable for individuals with some experience in the field.
     * Senior: Advanced skills and expertise, suitable for experienced professionals or specialists.
     */
}
