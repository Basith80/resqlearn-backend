package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {}
