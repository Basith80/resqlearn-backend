package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.AssessmentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssessmentAttemptRepository extends JpaRepository<AssessmentAttempt, Long> {

    /**
     * Get a user's attempt record for a specific disaster
     */
    Optional<AssessmentAttempt> findByUser_IdAndDisaster_Id(Long userId, Long disasterId);

    List<AssessmentAttempt> findByUser_Id(Long userId);

    /**
     * Leaderboard for a disaster:
     * ordered by leaderboard score DESC, then firstAttemptedAt ASC (tiebreaker).
     * Only includes users who have completed at least one attempt.
     */
    @Query("SELECT a FROM AssessmentAttempt a " +
            "WHERE a.disaster.id = :disasterId AND a.leaderboardScore IS NOT NULL " +
            "ORDER BY a.leaderboardScore DESC, a.firstAttemptedAt ASC")
    List<AssessmentAttempt> findLeaderboardByDisasterId(@Param("disasterId") Long disasterId);
}