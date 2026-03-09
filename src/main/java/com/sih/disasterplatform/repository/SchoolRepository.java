package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByName(String name);
    Optional<School> findByContactEmail(String contactEmail); // ✅ needed for school-scoped queries
    Optional<School> findByTeacherCode(String teacherCode);
    Optional<School> findByStudentCode(String studentCode);


    boolean existsByTeacherCode(String teacherCode);
    boolean existsByStudentCode(String studentCode);
}