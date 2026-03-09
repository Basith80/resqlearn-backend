package com.sih.disasterplatform.controller.drill;

import com.sih.disasterplatform.entity.DrillResult;
import com.sih.disasterplatform.service.drill.DrillResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drill-results")
public class DrillResultController {

    private final DrillResultService drillResultService;

    public DrillResultController(DrillResultService drillResultService) {
        this.drillResultService = drillResultService;
    }

    // ✅ Student or Individual submits a drill answer
    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL')")
    @PostMapping
    public ResponseEntity<DrillResult> submit(@RequestBody DrillResult result) {
        return ResponseEntity.ok(drillResultService.save(result));
    }

    // ✅ Student views their own drill results
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<DrillResult>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(drillResultService.getByStudentId(studentId));
    }

    // ✅ Individual views their own results by email
    @PreAuthorize("hasRole('INDIVIDUAL')")
    @GetMapping("/my-results")
    public ResponseEntity<List<DrillResult>> getMyResults(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(drillResultService.getByUserEmail(email));
    }

    // ✅ Teacher/School views all results for a drill
    @PreAuthorize("hasAnyRole('TEACHER','SCHOOL')")
    @GetMapping("/drill/{drillId}")
    public ResponseEntity<List<DrillResult>> getByDrill(@PathVariable Long drillId) {
        return ResponseEntity.ok(drillResultService.getByDrillId(drillId));
    }
}