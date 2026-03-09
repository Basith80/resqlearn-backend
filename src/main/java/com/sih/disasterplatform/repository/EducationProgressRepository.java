package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.EducationProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EducationProgressRepository extends JpaRepository<EducationProgress, Long> {

    /** Get a user's progress for one disaster */
    Optional<EducationProgress> findByUser_IdAndDisaster_Id(Long userId, Long disasterId);
}