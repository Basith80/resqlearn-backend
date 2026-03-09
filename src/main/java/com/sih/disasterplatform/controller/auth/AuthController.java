package com.sih.disasterplatform.controller.auth;

import com.sih.disasterplatform.dto.AuthRegisterDTO;
import com.sih.disasterplatform.dto.request.LoginRequest;
import com.sih.disasterplatform.dto.request.RegisterRequest;
import com.sih.disasterplatform.dto.response.LoginResponse;
import com.sih.disasterplatform.entity.*;
import com.sih.disasterplatform.repository.*;
import com.sih.disasterplatform.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    // ✅ PUBLIC - safe fields only, no codes exposed
    @GetMapping("/schools")
    public ResponseEntity<List<School>> getSchoolsPublic() {
        return ResponseEntity.ok(schoolRepository.findAll());
    }

    private String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }


    // ── PASTE THIS METHOD INTO YOUR AuthController.java ──────────────────────────
// Replace your existing register() method with this one.

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRegisterDTO dto) {
        try {
            // Check email not already taken
            if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already registered");
            }

            // Role enum — must convert String → Role enum
            Role role;
            try {
                role = Role.valueOf(dto.getRole());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role: " + dto.getRole());
            }

            // Build and save base User
            User user = User.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(role)
                    .enabled(true)
                    .build();
            user = userRepository.save(user);

            // Role-specific setup
            if (Role.SCHOOL.equals(role)) {
                // School registers freely — generates its own codes
                String teacherCode = generateCode();
                String studentCode = generateCode();
                School school = School.builder()
                        .name(dto.getName())
                        .contactEmail(dto.getEmail())
                        .teacherCode(teacherCode)
                        .studentCode(studentCode)
                        .build();
                schoolRepository.save(school);

            } else if (Role.TEACHER.equals(role)) {
                // Find school by teacherCode
                School school = schoolRepository.findByTeacherCode(dto.getSecretCode())
                        .orElseThrow(() -> new RuntimeException("Invalid teacher code. Check the code given by your institution."));
                Teacher teacher = Teacher.builder()
                        .user(user)
                        .school(school)
                        .build();
                teacherRepository.save(teacher);

            } else if (Role.STUDENT.equals(role)) {
                // Find school by studentCode
                School school = schoolRepository.findByStudentCode(dto.getSecretCode())
                        .orElseThrow(() -> new RuntimeException("Invalid student code. Check the code given by your institution."));
                Student student = Student.builder()
                        .user(user)
                        .school(school)
                        .build();
                studentRepository.save(student);
            }
            // INDIVIDUAL — only the User record is needed, nothing else

            return ResponseEntity.ok("Registration successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

// ── Add these 2 lines to SchoolRepository.java ───────────────────────────────
// Optional<School> findByTeacherCode(String teacherCode);
// Optional<School> findByStudentCode(String studentCode);

    // ── Also add these two methods to your SchoolRepository.java ─────────────────
// Optional<School> findByTeacherCode(String teacherCode);
// Optional<School> findByStudentCode(String studentCode);
    // ✅ LOGIN - now returns fullName too so sidebar can show it
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // ✅ Return fullName so frontend can store it in localStorage
        return ResponseEntity.ok(new LoginResponse(token, user.getRole().name(), user.getName()));
    }
}