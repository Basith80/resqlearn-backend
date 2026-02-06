package com.sih.disasterplatform.dashboard.service;

import com.sih.disasterplatform.dashboard.dto.DashboardResponse;
import com.sih.disasterplatform.disaster.repository.DisasterEducationProgressRepository;
import com.sih.disasterplatform.disaster.repository.SchoolDisasterAccessRepository;
import com.sih.disasterplatform.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DisasterEducationProgressRepository educationRepo;
    private final SchoolDisasterAccessRepository schoolAccessRepo;

    @Override
    public DashboardResponse getDashboard() {

        Long userId = SecurityUtil.getCurrentUserId();
        Long schoolId = SecurityUtil.getCurrentSchoolId();

        long completed = educationRepo.countByUserIdAndCompletedTrue(userId);
        long unlocked = schoolAccessRepo.countBySchoolIdAndUnlockedTrue(schoolId);

        return new DashboardResponse(
                unlocked,
                completed,
                unlocked
        );
    }
}
