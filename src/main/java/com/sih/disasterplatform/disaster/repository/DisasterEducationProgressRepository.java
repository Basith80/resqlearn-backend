package com.sih.disasterplatform.disaster.repository;

import com.sih.disasterplatform.disaster.entity.DisasterEducationProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisasterEducationProgressRepository
        extends JpaRepository<DisasterEducationProgress, Long> {

    Optional<DisasterEducationProgress> findByUserIdAndDisasterId(Long userId, Long disasterId);

    boolean existsByUserIdAndDisasterIdAndCompletedTrue(Long userId, Long disasterId);

    long countByUserIdAndCompletedTrue(Long userId);
}
