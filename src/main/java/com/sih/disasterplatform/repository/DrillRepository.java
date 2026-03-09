package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.VirtualDrill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrillRepository extends JpaRepository<VirtualDrill, Long> {
    List<VirtualDrill> findByDisaster_Id(Long disasterId);
}
