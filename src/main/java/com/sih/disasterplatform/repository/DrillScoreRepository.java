package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.DrillScore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DrillScoreRepository extends JpaRepository<DrillScore, Long> {

    /** Get one user's best score for one disaster */
    Optional<DrillScore> findByUser_IdAndDisaster_Id(Long userId, Long disasterId);

    /** Get all high scores for a disaster (for leaderboard display if needed later) */
    List<DrillScore> findByDisaster_Id(Long disasterId);
}