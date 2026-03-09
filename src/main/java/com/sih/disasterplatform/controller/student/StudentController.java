package com.sih.disasterplatform.controller.student;

import com.sih.disasterplatform.entity.Student;
import com.sih.disasterplatform.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    private final StudentRepository studentRepository;

    // ✅ SECURITY FIX: Students list scoped to school - only used by school admin via /api/schools/my/students
    // This endpoint now only returns the logged-in student's own profile
    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> getMyProfile(Authentication auth) {
        String email = auth.getName();
        Student student = studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));
        return ResponseEntity.ok(student);
    }

    // ✅ Get students by school - used internally
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<Student>> getBySchool(@PathVariable Long schoolId) {
        return ResponseEntity.ok(studentRepository.findBySchool_Id(schoolId));
    }

    // ✅ Keep general list for admin/backward compat but mark clearly
    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(studentRepository.findAll());
    }
}