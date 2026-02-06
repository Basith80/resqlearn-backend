package com.sih.disasterplatform.student.controller;

import com.sih.disasterplatform.disaster.service.EducationProgressService;
import com.sih.disasterplatform.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/education")
@RequiredArgsConstructor
public class StudentEducationController {

    private final EducationProgressService educationService;

    @PostMapping("/{disasterId}/complete")
    public void complete(@PathVariable Long disasterId) {
        educationService.markCompleted(SecurityUtil.getCurrentUserId(), disasterId);
    }
}
