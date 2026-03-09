package com.sih.disasterplatform.controller.assessment;

import com.sih.disasterplatform.entity.AssessmentResult;
import com.sih.disasterplatform.service.assessment.AssessmentResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessment-results")
public class AssessmentResultController {

    private final AssessmentResultService service;

    public AssessmentResultController(AssessmentResultService service) {
        this.service = service;
    }

    @PostMapping
    public AssessmentResult create(@RequestBody AssessmentResult result) {
        return service.save(result);
    }

    @GetMapping
    public List<AssessmentResult> getAll() {
        return service.getAll();
    }

    @GetMapping("/student/{studentId}")
    public List<AssessmentResult> getByStudent(@PathVariable Long studentId) {
        return service.getByStudentId(studentId);
    }

    @GetMapping("/assessment/{assessmentId}")
    public List<AssessmentResult> getByAssessment(@PathVariable Long assessmentId) {
        return service.getByAssessmentId(assessmentId);
    }
}
