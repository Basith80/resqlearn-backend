package com.sih.disasterplatform.service.school;

import com.sih.disasterplatform.entity.School;
import com.sih.disasterplatform.repository.SchoolRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School createSchool(School school) {
        return schoolRepository.save(school);
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    public School getSchoolById(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
    }

    public School updateSchool(Long id, School school) {
        School existing = getSchoolById(id);
        existing.setName(school.getName());
        existing.setAddress(school.getAddress());
        existing.setDistrict(school.getDistrict());
        return schoolRepository.save(existing);
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }
}
