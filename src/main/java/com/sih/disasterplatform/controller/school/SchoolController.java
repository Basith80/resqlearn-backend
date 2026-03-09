package com.sih.disasterplatform.controller.school;

import com.sih.disasterplatform.entity.*;
import com.sih.disasterplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SchoolController {

    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        return ResponseEntity.ok(schoolRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getById(@PathVariable Long id) {
        return schoolRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ SECURITY FIX: Only return teachers belonging to THIS school
    @GetMapping("/my/teachers")
    @PreAuthorize("hasRole('SCHOOL')")
    public ResponseEntity<List<Teacher>> getMyTeachers(Authentication auth) {
        String email = auth.getName();
        School school = schoolRepository.findByContactEmail(email)
                .orElseThrow(() -> new RuntimeException("School not found"));
        return ResponseEntity.ok(teacherRepository.findBySchool_Id(school.getId()));
    }

    // ✅ SECURITY FIX: Only return students belonging to THIS school
    @GetMapping("/my/students")
    @PreAuthorize("hasRole('SCHOOL')")
    public ResponseEntity<List<Student>> getMyStudents(Authentication auth) {
        String email = auth.getName();
        School school = schoolRepository.findByContactEmail(email)
                .orElseThrow(() -> new RuntimeException("School not found"));
        return ResponseEntity.ok(studentRepository.findBySchool_Id(school.getId()));
    }

    // ✅ REMOVE MEMBER - verifies name, email, and correct school code
    @DeleteMapping("/my/remove-member")
    @PreAuthorize("hasRole('SCHOOL')")
    public ResponseEntity<String> removeMember(
            @RequestBody RemoveMemberRequest req,
            Authentication auth) {

        String schoolEmail = auth.getName();
        School school = schoolRepository.findByContactEmail(schoolEmail)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // Validate the correct code was provided
        if (req.getMemberType().equalsIgnoreCase("TEACHER")) {
            if (!school.getTeacherCode().equalsIgnoreCase(req.getSecurityCode()))
                return ResponseEntity.badRequest().body("Invalid teacher security code");

            Teacher teacher = teacherRepository.findBySchool_Id(school.getId())
                    .stream()
                    .filter(t -> t.getUser().getEmail().equalsIgnoreCase(req.getEmail())
                            && t.getUser().getName().equalsIgnoreCase(req.getName()))
                    .findFirst()
                    .orElse(null);

            if (teacher == null)
                return ResponseEntity.badRequest().body("No teacher found with that name and email in your school");

            teacherRepository.delete(teacher);
            userRepository.delete(teacher.getUser());
            return ResponseEntity.ok("Teacher removed successfully");

        } else if (req.getMemberType().equalsIgnoreCase("STUDENT")) {
            if (!school.getStudentCode().equalsIgnoreCase(req.getSecurityCode()))
                return ResponseEntity.badRequest().body("Invalid student security code");

            Student student = studentRepository.findBySchool_Id(school.getId())
                    .stream()
                    .filter(s -> s.getUser().getEmail().equalsIgnoreCase(req.getEmail())
                            && s.getUser().getName().equalsIgnoreCase(req.getName()))
                    .findFirst()
                    .orElse(null);

            if (student == null)
                return ResponseEntity.badRequest().body("No student found with that name and email in your school");

            studentRepository.delete(student);
            userRepository.delete(student.getUser());
            return ResponseEntity.ok("Student removed successfully");

        } else {
            return ResponseEntity.badRequest().body("memberType must be TEACHER or STUDENT");
        }
    }

    // DTO for remove request
    public static class RemoveMemberRequest {
        private String memberType; // "TEACHER" or "STUDENT"
        private String name;
        private String email;
        private String securityCode;

        public String getMemberType()   { return memberType; }
        public String getName()         { return name; }
        public String getEmail()        { return email; }
        public String getSecurityCode() { return securityCode; }
    }
}