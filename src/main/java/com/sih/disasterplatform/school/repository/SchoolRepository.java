package com.sih.disasterplatform.school.repository;

import com.sih.disasterplatform.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
