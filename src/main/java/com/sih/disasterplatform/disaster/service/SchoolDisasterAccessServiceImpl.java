package com.sih.disasterplatform.disaster.service;

import com.sih.disasterplatform.disaster.entity.SchoolDisasterAccess;
import com.sih.disasterplatform.disaster.repository.SchoolDisasterAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolDisasterAccessServiceImpl
        implements SchoolDisasterAccessService {

    private final SchoolDisasterAccessRepository repository;

    @Override
    public void unlock(Long schoolId, Long disasterId) {
        SchoolDisasterAccess access = new SchoolDisasterAccess();
        access.setSchoolId(schoolId);
        access.setDisasterId(disasterId);
        access.setUnlocked(true);
        repository.save(access);
    }

    @Override
    public boolean isUnlocked(Long schoolId, Long disasterId) {
        return repository
                .existsBySchoolIdAndDisasterIdAndUnlockedTrue(schoolId, disasterId);
    }
}
