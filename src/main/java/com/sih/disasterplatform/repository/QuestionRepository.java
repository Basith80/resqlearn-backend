package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    /** All questions for a given assessment */
    List<Question> findByAssessment_Id(Long assessmentId);

    /** Questions for a disaster's assessment (joined through assessment → disaster) */
    List<Question> findByAssessment_Disaster_Id(Long disasterId);
}