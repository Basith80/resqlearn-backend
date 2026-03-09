package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByDisaster_Id(Long disasterId);

    Optional<Assessment> findFirstByDisaster_Id(Long disasterId);
}