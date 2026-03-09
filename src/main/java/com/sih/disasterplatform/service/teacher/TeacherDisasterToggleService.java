package com.sih.disasterplatform.service.teacher;

import com.sih.disasterplatform.entity.*;
import com.sih.disasterplatform.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherDisasterToggleService {

    private final SchoolDisasterAccessRepository accessRepository;
    private final TeacherRepository teacherRepository;
    private final DisasterRepository disasterRepository;

    public TeacherDisasterToggleService(SchoolDisasterAccessRepository accessRepository,
                                        TeacherRepository teacherRepository,
                                        DisasterRepository disasterRepository) {
        this.accessRepository = accessRepository;
        this.teacherRepository = teacherRepository;
        this.disasterRepository = disasterRepository;
    }

    // ✅ Teacher enables/disables a disaster for THEIR school only
    public SchoolDisasterAccess toggleDisaster(String teacherEmail, Long disasterId, boolean enable) {

        Teacher teacher = teacherRepository.findByUser_Email(teacherEmail)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        School school = teacher.getSchool();

        Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        SchoolDisasterAccess access = accessRepository
                .findBySchool_IdAndDisaster_Id(school.getId(), disasterId)
                .orElse(SchoolDisasterAccess.builder()
                        .school(school)
                        .disaster(disaster)
                        .build());

        access.setEnabled(enable);
        return accessRepository.save(access);
    }

    // ✅ Get all enabled disasters for a school (used by students)
    public List<SchoolDisasterAccess> getEnabledDisastersForSchool(Long schoolId) {
        return accessRepository.findBySchool_IdAndEnabledTrue(schoolId);
    }

    // ✅ Get all disasters access status for a school
    public List<SchoolDisasterAccess> getAllForSchool(Long schoolId) {
        return accessRepository.findBySchool_Id(schoolId);
    }
}