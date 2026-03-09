package com.sih.disasterplatform.controller.education;

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
 * Handles saving and loading education paragraph progress.
 *
 * GET  /api/education/progress/disaster/{disasterId}
 *      → Returns current progress (or null if none yet)
 *
 * POST /api/education/progress/unlock
 *      Body: { disasterId, paragraphIndex, completed }
 *      → Saves progress, returns updated EducationProgress
 */
@RestController
@RequestMapping("/api/education/progress")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class EducationProgressController {

    private final EducationProgressRepository progressRepo;
    private final UserRepository userRepository;
    private final DisasterRepository disasterRepository;

    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL','TEACHER','SCHOOL')")
    @GetMapping("/disaster/{disasterId}")
    public ResponseEntity<?> getProgress(
            @PathVariable Long disasterId,
            Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Return the progress object, or null JSON if no progress exists yet
        return progressRepo.findByUser_IdAndDisaster_Id(user.getId(), disasterId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
    }

    @PreAuthorize("hasAnyRole('STUDENT','INDIVIDUAL','TEACHER','SCHOOL')")
    @PostMapping("/unlock")
    public ResponseEntity<?> unlockParagraph(
            @RequestBody Map<String, Object> body,
            Authentication auth) {

        Long disasterId     = Long.valueOf(body.get("disasterId").toString());
        int  paragraphIndex = Integer.parseInt(body.get("paragraphIndex").toString());
        boolean isCompleted = Boolean.parseBoolean(body.get("completed").toString());

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        // Get existing progress or create a new one
        EducationProgress progress = progressRepo
                .findByUser_IdAndDisaster_Id(user.getId(), disasterId)
                .orElse(EducationProgress.builder()
                        .user(user)
                        .disaster(disaster)
                        .unlockedUpTo(-1)
                        .completed(false)
                        .build());

        // Never go backward — only advance
        if (paragraphIndex > progress.getUnlockedUpTo()) {
            progress.setUnlockedUpTo(paragraphIndex);
        }

        // Mark complete if this is the final paragraph
        if (isCompleted && !progress.isCompleted()) {
            progress.setCompleted(true);
            progress.setCompletedAt(LocalDateTime.now());
        }

        return ResponseEntity.ok(progressRepo.save(progress));
    }
}