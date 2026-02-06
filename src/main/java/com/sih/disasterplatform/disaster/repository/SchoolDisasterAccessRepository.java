package com.sih.disasterplatform.disaster.repository;

import com.sih.disasterplatform.disaster.entity.SchoolDisasterAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolDisasterAccessRepository
        extends JpaRepository<SchoolDisasterAccess, Long> {

    boolean existsBySchoolIdAndDisasterIdAndUnlockedTrue(Long schoolId, Long disasterId);

    long countBySchoolIdAndUnlockedTrue(Long schoolId);
}
