package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
    List<AssessmentResult> findByStudent_Id(Long studentId);
    List<AssessmentResult> findByAssessment_Id(Long assessmentId);
}
