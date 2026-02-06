package com.sih.disasterplatform.disaster.service;

import com.sih.disasterplatform.disaster.entity.DisasterEducationProgress;
import com.sih.disasterplatform.disaster.repository.DisasterEducationProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationProgressServiceImpl implements EducationProgressService {

    private final DisasterEducationProgressRepository repository;

    @Override
    public void markCompleted(Long userId, Long disasterId) {
        DisasterEducationProgress progress =
                repository.findByUserIdAndDisasterId(userId, disasterId)
                        .orElseGet(() -> {
                            DisasterEducationProgress p = new DisasterEducationProgress();
                            p.setUserId(userId);
                            p.setDisasterId(disasterId);
                            return p;
                        });

        progress.setCompleted(true);
        repository.save(progress);
    }

    @Override
    public boolean isCompleted(Long userId, Long disasterId) {
        return repository.existsByUserIdAndDisasterIdAndCompletedTrue(userId, disasterId);
    }

    @Override
    public long completedCount(Long userId) {
        return repository.countByUserIdAndCompletedTrue(userId);
    }
}
