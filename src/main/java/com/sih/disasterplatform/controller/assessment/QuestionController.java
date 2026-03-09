package com.sih.disasterplatform.controller.assessment;

import com.sih.disasterplatform.entity.Question;
import com.sih.disasterplatform.service.assessment.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // ✅ Only TEACHER/SCHOOL can add questions
    @PreAuthorize("hasAnyRole('TEACHER','SCHOOL')")
    @PostMapping
    public ResponseEntity<Question> create(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.save(question));
    }

    // ✅ All users can fetch questions for an assessment
    @PreAuthorize("hasAnyRole('SCHOOL','TEACHER','STUDENT','INDIVIDUAL')")
    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<Question>> getByAssessment(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(questionService.getByAssessmentId(assessmentId));
    }

    // ✅ Only TEACHER/SCHOOL can delete
    @PreAuthorize("hasAnyRole('TEACHER','SCHOOL')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok().build();
    }
}