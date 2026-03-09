package com.sih.disasterplatform.controller.teacher;

import com.sih.disasterplatform.entity.*;
import com.sih.disasterplatform.repository.*;
import com.sih.disasterplatform.service.teacher.TeacherDisasterToggleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final TeacherDisasterToggleService toggleService;

    // Teacher's own profile
    @GetMapping("/me")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Teacher> getMyProfile(Authentication auth) {
        String email = auth.getName();
        Teacher teacher = teacherRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Teacher profile not found"));
        return ResponseEntity.ok(teacher);
    }

    // Only students from teacher's own school
    @GetMapping("/my/students")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<Student>> getMySchoolStudents(Authentication auth) {
        String email = auth.getName();
        Teacher teacher = teacherRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        List<Student> students = studentRepository.findBySchool_Id(teacher.getSchool().getId());
        return ResponseEntity.ok(students);
    }

    // Toggle disaster module for school
    @PutMapping("/toggle-disaster/{disasterId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<SchoolDisasterAccess> toggleDisaster(
            @PathVariable Long disasterId,
            @RequestParam boolean enable,
            Authentication auth) {
        SchoolDisasterAccess result = toggleService.toggleDisaster(auth.getName(), disasterId, enable);
        return ResponseEntity.ok(result);
    }

    // Get enabled disasters for a school
    @GetMapping("/school/{schoolId}/disasters")
    public ResponseEntity<List<SchoolDisasterAccess>> getEnabledDisasters(@PathVariable Long schoolId) {
        return ResponseEntity.ok(toggleService.getEnabledDisastersForSchool(schoolId));
    }

    // ✅ FIX: was getAllDisastersForSchool() — correct name is getAllForSchool()
    @GetMapping("/school/{schoolId}/disasters/all")
    public ResponseEntity<List<SchoolDisasterAccess>> getAllDisastersForSchool(@PathVariable Long schoolId) {
        return ResponseEntity.ok(toggleService.getAllForSchool(schoolId));
    }

    // Keep general list for backward compat
    @GetMapping
    public ResponseEntity<List<Teacher>> getAll() {
        return ResponseEntity.ok(teacherRepository.findAll());
    }
}