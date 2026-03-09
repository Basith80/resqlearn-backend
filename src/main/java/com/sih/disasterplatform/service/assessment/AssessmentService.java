package com.sih.disasterplatform.service.assessment;

import com.sih.disasterplatform.entity.Assessment;
import com.sih.disasterplatform.repository.AssessmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;

    public AssessmentService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    public Assessment save(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    public List<Assessment> getAll() {
        return assessmentRepository.findAll();
    }

    public List<Assessment> getByDisasterId(Long disasterId) {
        return assessmentRepository.findByDisaster_Id(disasterId);
    }
}
