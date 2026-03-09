package com.sih.disasterplatform.controller.assessment;

import com.sih.disasterplatform.entity.*;
import com.sih.disasterplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Manages assessment attempts, penalties, and leaderboard.
 * <p>
 * GET  /api/assessment-attempts/disaster/{disasterId}
 * → Current attempt record for the logged-in user
 * <p>
 * POST /api/assessment-attempts/cancel
 * Body: { disasterId }
 * → Apply cancel penalty (-3 to denominator, min 10)
 * <p>
 * POST /api/assessment-attempts/submit
 * Body: { disasterId, rawCorrect }
 * → Save result, apply fail penalty for next attempt if failed, return score details
 * <p>
 * GET  /api/assessment-attempts/leaderboard/disaster/{disasterId}
 * → Ranked list of first-attempt scores for a disaster
 * <p>
 * GET  /api/assessments/questions/disaster/{disasterId}
 * → All 25 questions for a disaster's assessment (for frontend to show quiz)
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AssessmentAttemptController {

    private final AssessmentAttemptRepository attemptRepo;
    private final QuestionRepository questionRepo;
    private final UserRepository userRepository;
    private final DisasterRepository disasterRepository;

    // ─── Get current attempt info ─────────────────────────────────────────────
    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL','TEACHER','SCHOOL')")
    @GetMapping("/api/assessment-attempts/disaster/{disasterId}")
    public ResponseEntity<?> getAttemptInfo(
            @PathVariable Long disasterId,
            Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return attemptRepo.findByUser_IdAndDisaster_Id(user.getId(), disasterId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
    }

    // ─── Cancel mid-assessment ────────────────────────────────────────────────
    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL')")
    @PostMapping("/api/assessment-attempts/cancel")
    public ResponseEntity<?> cancelAssessment(
            @RequestBody Map<String, Object> body,
            Authentication auth) {

        Long disasterId = Long.valueOf(body.get("disasterId").toString());

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        AssessmentAttempt attempt = getOrCreateAttempt(user, disaster);

        attempt.setCancelCount(attempt.getCancelCount() + 1);
        // Cancel penalty: -3 per cancel, minimum 10
        int newDenom = Math.max(10, attempt.getCurrentDenominator() - 3);
        attempt.setCurrentDenominator(newDenom);

        attemptRepo.save(attempt);

        return ResponseEntity.ok(Map.of(
                "nextDenominator", attempt.getCurrentDenominator(),
                "cancelCount", attempt.getCancelCount()
        ));
    }

    // ─── Submit completed assessment ──────────────────────────────────────────
    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL')")
    @PostMapping("/api/assessment-attempts/submit")
    public ResponseEntity<?> submitAssessment(
            @RequestBody Map<String, Object> body,
            Authentication auth) {

        Long disasterId = Long.valueOf(body.get("disasterId").toString());
        int rawCorrect = Integer.parseInt(body.get("rawCorrect").toString()); // 0–25

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        AssessmentAttempt attempt = getOrCreateAttempt(user, disaster);
        boolean isFirstAttempt = attempt.getAttemptCount() == 0;

        // Record first attempt timestamp for leaderboard tiebreaker
        if (isFirstAttempt) {
            attempt.setFirstAttemptedAt(LocalDateTime.now());
        }

        // Current denominator (before this attempt finishes)
        int denominator = attempt.getCurrentDenominator();

        // Effective score = proportional to denominator
        // e.g. rawCorrect=18 out of 25, denominator=20 → (18/25)*20 = 14.4 → 14
        int effectiveScore = (int) Math.floor((rawCorrect / 25.0) * denominator);

        // Passing threshold = 60% of denominator (so 15/25 is always the raw pass mark)
        boolean passed = rawCorrect >= 15;

        // Update attempt record
        attempt.setAttemptCount(attempt.getAttemptCount() + 1);
        attempt.setLastRawScore(rawCorrect);
        attempt.setLastEffectiveScore(effectiveScore);
        attempt.setLastDenominator(denominator);

        // Save leaderboard data from FIRST attempt only
        if (isFirstAttempt) {
            attempt.setLeaderboardRawScore(rawCorrect);
            attempt.setLeaderboardScore(effectiveScore);
            attempt.setLeaderboardDenominator(denominator);
        }

        // Apply FAIL penalty for NEXT attempt (after this one)
        if (!passed) {
            attempt.setFailCount(attempt.getFailCount() + 1);
            int nextDenom = denominator;
            if (nextDenom > 20) nextDenom = 20;
            else if (nextDenom > 15) nextDenom = 15;
            else if (nextDenom > 10) nextDenom = 10;
            // If already 10, stays 10
            attempt.setCurrentDenominator(nextDenom);
        }

        attemptRepo.save(attempt);

        return ResponseEntity.ok(Map.of(
                "rawCorrect", rawCorrect,
                "effectiveScore", effectiveScore,
                "denominator", denominator,
                "passed", passed,
                "isFirstAttempt", isFirstAttempt,
                "leaderboardScore", attempt.getLeaderboardScore() != null ? attempt.getLeaderboardScore() : effectiveScore,
                "nextDenominator", attempt.getCurrentDenominator(),
                "attemptCount", attempt.getAttemptCount()
        ));
    }

    // ─── Leaderboard for a disaster ───────────────────────────────────────────
    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL','TEACHER','SCHOOL')")
    @GetMapping("/api/assessment-attempts/leaderboard/disaster/{disasterId}")
    public ResponseEntity<List<AssessmentAttempt>> getLeaderboard(@PathVariable Long disasterId) {
        return ResponseEntity.ok(
                attemptRepo.findLeaderboardByDisasterId(disasterId)
        );
    }

    // ─── Questions for a disaster's assessment ────────────────────────────────
    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL','TEACHER','SCHOOL')")
    @GetMapping("/api/assessments/questions/disaster/{disasterId}")
    public ResponseEntity<?> getQuestionsByDisaster(@PathVariable Long disasterId) {
        List<Question> questions = questionRepo.findByAssessment_Disaster_Id(disasterId);
        return ResponseEntity.ok(questions);
    }

    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL','TEACHER','SCHOOL')")
    @GetMapping("/api/assessment-attempts/student/{studentId}")
    public ResponseEntity<List<AssessmentAttempt>> getStudentAttempts(@PathVariable Long studentId) {
        return ResponseEntity.ok(attemptRepo.findByUser_Id(studentId));
    }

    // ─── Helper ───────────────────────────────────────────────────────────────
    private AssessmentAttempt getOrCreateAttempt(User user, Disaster disaster) {
        return attemptRepo
                .findByUser_IdAndDisaster_Id(user.getId(), disaster.getId())
                .orElse(AssessmentAttempt.builder()
                        .user(user)
                        .disaster(disaster)
                        .attemptCount(0)
                        .failCount(0)
                        .cancelCount(0)
                        .currentDenominator(25)
                        .build());
    }
}