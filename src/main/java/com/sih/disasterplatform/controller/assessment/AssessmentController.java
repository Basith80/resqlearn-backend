package com.sih.disasterplatform.controller.assessment;

import com.sih.disasterplatform.entity.Assessment;
import com.sih.disasterplatform.entity.AssessmentResult;
import com.sih.disasterplatform.service.assessment.AssessmentService;
import com.sih.disasterplatform.service.assessment.AssessmentResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final AssessmentResultService resultService;

    public AssessmentController(AssessmentService assessmentService,
                                AssessmentResultService resultService) {
        this.assessmentService = assessmentService;
        this.resultService = resultService;
    }

    // ✅ Only TEACHER creates assessments
    @PreAuthorize("hasAnyRole('TEACHER','SCHOOL')")
    @PostMapping
    public ResponseEntity<Assessment> create(@RequestBody Assessment assessment) {
        return ResponseEntity.ok(assessmentService.save(assessment));
    }

    // ✅ All roles can view assessments
    @PreAuthorize("hasAnyRole('SCHOOL','TEACHER','STUDENT','INDIVIDUAL')")
    @GetMapping
    public ResponseEntity<List<Assessment>> getAll() {
        return ResponseEntity.ok(assessmentService.getAll());
    }

    @PreAuthorize("hasAnyRole('SCHOOL','TEACHER','STUDENT','INDIVIDUAL')")
    @GetMapping("/disaster/{disasterId}")
    public ResponseEntity<List<Assessment>> getByDisaster(@PathVariable Long disasterId) {
        return ResponseEntity.ok(assessmentService.getByDisasterId(disasterId));
    }

    // ✅ Submit assessment result (STUDENT or INDIVIDUAL)
    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL')")
    @PostMapping("/submit")
    public ResponseEntity<AssessmentResult> submit(@RequestBody AssessmentResult result) {
        return ResponseEntity.ok(resultService.save(result));
    }

    // ✅ STUDENT: view own results + classmates (same school)
    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL')")
    @GetMapping("/results/student/{studentId}")
    public ResponseEntity<List<AssessmentResult>> getResultsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(resultService.getByStudentId(studentId));
    }

    // ✅ TEACHER/SCHOOL: view all results for an assessment
    @PreAuthorize("hasAnyRole('TEACHER','SCHOOL')")
    @GetMapping("/results/assessment/{assessmentId}")
    public ResponseEntity<List<AssessmentResult>> getResultsByAssessment(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(resultService.getByAssessmentId(assessmentId));
    }

    // ✅ All results (school admin use)
    @PreAuthorize("hasRole('SCHOOL')")
    @GetMapping("/results")
    public ResponseEntity<List<AssessmentResult>> getAllResults() {
        return ResponseEntity.ok(resultService.getAll());
    }
}