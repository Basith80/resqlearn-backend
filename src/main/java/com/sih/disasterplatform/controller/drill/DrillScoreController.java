package com.sih.disasterplatform.controller.drill;

import com.sih.disasterplatform.entity.*;
import com.sih.disasterplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * GET  /api/drill-scores/disaster/{disasterId}
 *      → Returns current personal best (or null if never played)
 *
 * POST /api/drill-scores/save
 *      Body: { disasterId, score, totalQuestions }
 *      → Saves score only if it beats the existing high score
 *      → Returns { highScore, totalQuestions, isNewBest }
 */
@RestController
@RequestMapping("/api/drill-scores")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DrillScoreController {

    private final DrillScoreRepository drillScoreRepo;
    private final UserRepository userRepository;
    private final DisasterRepository disasterRepository;

    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL','TEACHER','SCHOOL')")
    @GetMapping("/disaster/{disasterId}")
    public ResponseEntity<?> getMyScore(
            @PathVariable Long disasterId,
            Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return drillScoreRepo.findByUser_IdAndDisaster_Id(user.getId(), disasterId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
    }

    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL','TEACHER','SCHOOL')")
    @PostMapping("/save")
    public ResponseEntity<?> saveDrillScore(
            @RequestBody Map<String, Object> body,
            Authentication auth) {

        Long disasterId      = Long.valueOf(body.get("disasterId").toString());
        int  newScore        = Integer.parseInt(body.get("score").toString());
        int  totalQuestions  = Integer.parseInt(body.get("totalQuestions").toString());

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        DrillScore existing = drillScoreRepo
                .findByUser_IdAndDisaster_Id(user.getId(), disasterId)
                .orElse(DrillScore.builder()
                        .user(user)
                        .disaster(disaster)
                        .highScore(0)
                        .totalQuestions(totalQuestions)
                        .build());

        boolean isNewBest = newScore > existing.getHighScore();

        if (isNewBest) {
            existing.setHighScore(newScore);
            existing.setTotalQuestions(totalQuestions);
            existing.setAchievedAt(LocalDateTime.now());
            drillScoreRepo.save(existing);
        }

        return ResponseEntity.ok(Map.of(
                "highScore",      existing.getHighScore(),
                "totalQuestions", existing.getTotalQuestions(),
                "isNewBest",      isNewBest
        ));
    }
}