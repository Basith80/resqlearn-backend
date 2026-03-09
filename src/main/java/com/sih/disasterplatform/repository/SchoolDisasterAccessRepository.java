package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.SchoolDisasterAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolDisasterAccessRepository extends JpaRepository<SchoolDisasterAccess, Long> {

    List<SchoolDisasterAccess> findBySchool_Id(Long schoolId);

    List<SchoolDisasterAccess> findBySchool_IdAndEnabledTrue(Long schoolId);

    Optional<SchoolDisasterAccess> findBySchool_IdAndDisaster_Id(Long schoolId, Long disasterId);
}