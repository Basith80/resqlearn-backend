package com.sih.disasterplatform.service.assessment;

import com.sih.disasterplatform.entity.AssessmentResult;
import com.sih.disasterplatform.repository.AssessmentResultRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssessmentResultService {

    private final AssessmentResultRepository repository;

    public AssessmentResultService(AssessmentResultRepository repository) {
        this.repository = repository;
    }

    public AssessmentResult save(AssessmentResult result) {
        return repository.save(result);
    }

    public List<AssessmentResult> getAll() {
        return repository.findAll();
    }

    public List<AssessmentResult> getByStudentId(Long studentId) {
        return repository.findByStudent_Id(studentId);
    }

    public List<AssessmentResult> getByAssessmentId(Long assessmentId) {
        return repository.findByAssessment_Id(assessmentId);
    }
}
