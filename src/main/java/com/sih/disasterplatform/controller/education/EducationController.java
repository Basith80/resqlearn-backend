package com.sih.disasterplatform.controller.education;

import com.sih.disasterplatform.entity.EducationContent;
import com.sih.disasterplatform.entity.SchoolDisasterAccess;
import com.sih.disasterplatform.entity.Student;
import com.sih.disasterplatform.repository.StudentRepository;
import com.sih.disasterplatform.repository.UserRepository;
import com.sih.disasterplatform.service.education.EducationService;
import com.sih.disasterplatform.service.teacher.TeacherDisasterToggleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
public class EducationController {

    private final EducationService educationService;
    private final TeacherDisasterToggleService toggleService;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public EducationController(EducationService educationService,
                               TeacherDisasterToggleService toggleService,
                               StudentRepository studentRepository,
                               UserRepository userRepository) {
        this.educationService = educationService;
        this.toggleService = toggleService;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    // ✅ SCHOOL/TEACHER creates education content
    @PreAuthorize("hasAnyRole('SCHOOL','TEACHER')")
    @PostMapping
    public ResponseEntity<EducationContent> create(@RequestBody EducationContent content) {
        return ResponseEntity.ok(educationService.save(content));
    }

    // ✅ Get all (admin/school view)
    @PreAuthorize("hasAnyRole('SCHOOL','TEACHER')")
    @GetMapping
    public ResponseEntity<List<EducationContent>> getAll() {
        return ResponseEntity.ok(educationService.getAll());
    }

    // ✅ INDIVIDUAL: can access any disaster education freely
    @PreAuthorize("hasRole('INDIVIDUAL')")
    @GetMapping("/disaster/{disasterId}")
    public ResponseEntity<List<EducationContent>> getByDisasterForIndividual(@PathVariable Long disasterId) {
        return ResponseEntity.ok(educationService.getByDisasterId(disasterId));
    }

    // ✅ STUDENT: can only access if teacher enabled this disaster for their school
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/disaster/{disasterId}")
    public ResponseEntity<?> getByDisasterForStudent(
            @PathVariable Long disasterId,
            Authentication authentication) {

        String email = authentication.getName();

        // Find student by email → user → student record
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        // Check if this disaster is enabled for student's school
        Long schoolId = student.getSchool().getId();
        List<SchoolDisasterAccess> enabledAccess = toggleService.getEnabledDisastersForSchool(schoolId);

        boolean isAllowed = enabledAccess.stream()
                .anyMatch(a -> a.getDisaster().getId().equals(disasterId));

        if (!isAllowed) {
            return ResponseEntity.status(403)
                    .body("This disaster module is not enabled by your teacher yet.");
        }

        return ResponseEntity.ok(educationService.getByDisasterId(disasterId));
    }
}