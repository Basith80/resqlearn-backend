package com.sih.disasterplatform.disaster.repository;

import com.sih.disasterplatform.disaster.entity.Disaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisasterRepository extends JpaRepository<Disaster, Long> {
}
