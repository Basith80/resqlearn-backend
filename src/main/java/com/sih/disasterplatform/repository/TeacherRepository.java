package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // ✅ Needed for school-scoped queries
    List<Teacher> findBySchool_Id(Long schoolId);
    // ✅ Needed for teacher profile & toggle
    Optional<Teacher> findByUser_Email(String email);
}