package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.Disaster;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DisasterRepository extends JpaRepository<Disaster, Long> {
    Optional<Disaster> findByName(String name);
}
