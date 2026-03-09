package com.sih.disasterplatform.repository;

import com.sih.disasterplatform.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student>     findBySchool_Id(Long schoolId);
    Optional<Student> findByUser_Email(String email);
    // ✅ FIX: EducationController uses findByUser_Id — added here
    Optional<Student> findByUser_Id(Long userId);
}