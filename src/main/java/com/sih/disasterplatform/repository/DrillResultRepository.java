package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.DrillResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrillResultRepository extends JpaRepository<DrillResult, Long> {
    List<DrillResult> findByStudent_Id(Long studentId);
    List<DrillResult> findByDrill_Id(Long drillId);
    List<DrillResult> findByUserEmail(String email);
}