package com.sih.disasterplatform.controller.superadmin;

import com.sih.disasterplatform.entity.User;
import com.sih.disasterplatform.entity.Role;
import com.sih.disasterplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    private final UserRepository userRepo;

    // ── Get all users ──────────────────────────────────────────────
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    // ── Get users by role ──────────────────────────────────────────
    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        try {
            Role r = Role.valueOf(role.toUpperCase());
            return ResponseEntity.ok(userRepo.findByRole(r));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── Stats overview ─────────────────────────────────────────────
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(Map.of(
                "schools",     userRepo.countByRole(Role.SCHOOL),
                "teachers",    userRepo.countByRole(Role.TEACHER),
                "students",    userRepo.countByRole(Role.STUDENT),
                "individuals", userRepo.countByRole(Role.INDIVIDUAL),
                "total",       userRepo.count()
        ));
    }

    // ── Delete single user ─────────────────────────────────────────
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        if (!userRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }

    // ── Delete all users of a role ─────────────────────────────────
    @DeleteMapping("/users/role/{role}")
    public ResponseEntity<Map<String, String>> deleteAllByRole(@PathVariable String role) {
        try {
            Role r = Role.valueOf(role.toUpperCase());
            List<User> users = userRepo.findByRole(r);
            userRepo.deleteAll(users);
            return ResponseEntity.ok(Map.of("message", users.size() + " users deleted"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── Search by email ────────────────────────────────────────────
    @GetMapping("/users/search")
    public ResponseEntity<User> searchByEmail(@RequestParam String email) {
        return userRepo.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}