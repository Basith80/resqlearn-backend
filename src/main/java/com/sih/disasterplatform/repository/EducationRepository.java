package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.EducationContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<EducationContent, Long> {
    List<EducationContent> findByDisaster_Id(Long disasterId);
}
